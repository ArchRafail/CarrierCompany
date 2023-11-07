import { Component, DestroyRef, inject, ViewChild } from '@angular/core';
import { PageDto } from "../../../api/models/page-dto";
import { TransporterDto } from "../../../api/models/transporter-dto";
import { debounce, distinctUntilChanged, finalize, interval, Subject } from "rxjs";
import { Table, TableLazyLoadEvent } from "primeng/table";
import { ConfirmationService, FilterMatchMode, FilterMetadata, MenuItem } from "primeng/api";
import { PrimengTableFilterCustomMatchMode } from "../../../models/primeng-table-filter-custom-match-mode";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Pageable } from "../../../api/models/pageable";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { DROPDOWN_BOOL_OPTIONS, PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
import { TransporterHttpService } from "../../../api/services/transporter-http.service";
import { primeNgTableFiltersToRequestParams } from "../../../helpers/PrimeNgHelper";
import { ToastService } from "../../../services/toast.service";
import { Router } from "@angular/router";
import { DeliveryHttpService } from "../../../api/services/delivery-http.service";


@Component({
  selector: 'app-transporter-list',
  templateUrl: './transporter-list.component.html',
  styleUrls: ['./transporter-list.component.scss'],
  providers: [ConfirmationService]
})
export class TransporterListComponent {
  private readonly destroyRef = inject(DestroyRef);
  public readonly CAPACITY_LOAD_RANGE: number[] = [2000, 30000];
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  public readonly DROPDOWN_BOOL_OPTIONS = DROPDOWN_BOOL_OPTIONS;
  @ViewChild("table") private table!: Table;
  transporterPage?: PageDto<TransporterDto>;
  loading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  loadCapacityRangeValues: number[] = [this.CAPACITY_LOAD_RANGE[0], this.CAPACITY_LOAD_RANGE[1]];
  contextMenuItems: MenuItem[] = [];
  selectedTransporter!: TransporterDto;

  constructor(private transporterHttpService: TransporterHttpService,
              private toastService: ToastService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private deliveryHttpService: DeliveryHttpService) {
    this.tableLazyLoad$
      .pipe(
        distinctUntilChanged(),
        debounce(event => {
          const initialFiltering = !this.filtersPreviousState;
          let shouldDebounce = Object.entries(event.filters as {[s: string]: FilterMetadata})
            .some(([key, filter]) =>
              filter && PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE.includes(filter.matchMode!)
              && filter.value !== this.filtersPreviousState?.[key]?.value)
          return interval(!initialFiltering && shouldDebounce ? PRIMENG_TABLE_FILTER_DEBOUNCE_TIME : 0);
        })
      ).subscribe( event => {
      this.getAll(primeNgTableFiltersToRequestParams(event.filters), Pageable.fromPrimeNg(event));
      this.filtersPreviousState = structuredClone(event.filters as {[s: string]: FilterMetadata});
    });
    this.initContextMenu();
  }

  initContextMenu() {
    this.contextMenuItems = [
      {
        label: 'Edit',
        icon: 'pi pi-pencil',
        command: () => this.edit()
      },
      {
        label: 'Activate',
        icon: 'pi pi-check-circle',
        command: () => this.updateActive(true),
        visible: this.selectedTransporter && !this.selectedTransporter.is_active
      },
      {
        label: 'Deactivate',
        icon: 'pi pi-trash',
        command: () => this.confirmDeactivation(),
        visible: this.selectedTransporter && this.selectedTransporter.is_active,
        styleClass: "menuitem-danger"
      },
    ];
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.loading = true;
    this.transporterPage = undefined;
    this.tableLazyLoad$.next(event);
  }

  private getAll(filters: any = {}, pageable: Pageable = new Pageable()){
    this.transporterHttpService.getAll(filters, pageable)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: page => this.transporterPage = page,
        error: this.toastService.handleHttpError
      });
  }

  addTransporter() {
    this.router.navigate(['/transporters/item/']);
  }

  private edit() {
    this.router.navigate([`/transporters/item/${this.selectedTransporter.id}`]);
  }

  private updateActive(isActive: boolean) {
    this.loading = true;
    this.transporterHttpService.updateActive(this.selectedTransporter.id, isActive)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: () => {
          if (isActive) {
            this.toastService.success(`Transporter was activated successfully!`);
          } else {
            this.toastService.success(`Transporter was deactivated successfully!`);
          }
          this.table.onLazyLoad.emit(this.table.createLazyLoadMetadata());
        },
        error: this.toastService.handleHttpError
      });
  }

  private confirmDeactivation() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to DEACTIVATE this transporter?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.checkDeactivationAbility()
    })
  }

  private checkDeactivationAbility() {
    this.loading = true;
    this.deliveryHttpService.getQuantityByTransporter(this.selectedTransporter.id)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: quantity => {
          if (quantity === 0) {
            this.updateActive(false);
          } else {
            this.toastService.warning(`Unable to deactivate transporter. Decline ${quantity} active deliveries with this transporter first.`)
          }
        },
        error: this.toastService.handleHttpError
      })
  }

  onContextMenuSelect(event: any) {
    this.initContextMenu();
  }
}
