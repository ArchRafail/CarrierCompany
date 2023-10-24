import { Component, DestroyRef, inject } from '@angular/core';
import { Router } from "@angular/router";
import { ToastService } from "../../../services/toast.service";
import { debounce, distinctUntilChanged, finalize, interval, Subject } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { DeliveryDto } from "../../../api/models/delivery-dto";
import { DeliveryHttpService } from "../../../api/services/delivery-http.service";
import { PageDto } from "../../../api/models/page-dto";
import { TableLazyLoadEvent } from "primeng/table";
import { ConfirmationService, FilterMatchMode, FilterMetadata, MenuItem } from "primeng/api";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
import { primeNgTableFiltersToRequestParams } from "../../../helpers/PrimeNgHelper";
import { Pageable } from "../../../api/models/pageable";
import { PrimengTableFilterCustomMatchMode } from "../../../models/primeng-table-filter-custom-match-mode";
import { DeliveryStatus } from "../../../api/models/delivery-status";


@Component({
  selector: 'app-delivery-list',
  templateUrl: './delivery-list.component.html',
  styleUrls: ['./delivery-list.component.scss']
})
export class DeliveryListComponent {
  private readonly destroyRef = inject(DestroyRef);
  public readonly MIN_CARGO_AMOUNT: number = 0;
  public readonly MAX_CARGO_AMOUNT: number = 40000;
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly DeliveryStatus = DeliveryStatus;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  deliveryPage?: PageDto<DeliveryDto>;
  isLoading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  cargoAmountRangeValues: number[] = [this.MIN_CARGO_AMOUNT, this.MAX_CARGO_AMOUNT];
  contextMenuItems: MenuItem[] = [];
  selectedDelivery!: DeliveryDto;
  statuses: string[] = Object.values(DeliveryStatus);

  constructor(private deliveryHttpService: DeliveryHttpService,
              private toastService: ToastService,
              private confirmationService: ConfirmationService,
              private router: Router
  ) {
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
    this.contextMenuItems = [
      {
        label: 'Edit',
        icon: 'pi pi-pencil',
        command: () => this.edit()
      },
      {
        label: 'Push',
        icon: 'pi pi-fast-forward',
        command: () => this.push()
      },
      {
        label: 'Decline',
        icon: 'pi pi-ban',
        command: () => this.confirmDecline()
      },
      {
        label: 'Delete',
        icon: 'pi pi-trash',
        command: () => this.confirmDelete()
      },
    ];
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.isLoading = true;
    this.deliveryPage = undefined;
    this.tableLazyLoad$.next(event);
  }

  private getAll(filters: any = {}, pageable: Pageable = new Pageable()){
    this.deliveryHttpService.getAll(filters, pageable)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: page => this.deliveryPage = page,
        error: this.toastService.handleHttpError
      });
  }

  addDelivery() {
    this.router.navigate(['/deliveries/item/']);
  }

  deleteById(id: number) {
    this.isLoading = true;
    this.deliveryHttpService.delete(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.getAll();
          this.toastService.success(`Delivery was deleted successfully!`)
        },
        error: this.toastService.handleHttpError
      });
  }

  edit() {
    this.router.navigate([`/deliveries/item/${this.selectedDelivery.id}`]);
  }

  private confirmDelete() {
    this.confirmDeleteById(this.selectedDelivery.id);
  }

  confirmDeleteById(id: number) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to DELETE this delivery?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.deleteById(id)
    })
  }

  private push() {
    this.pushById(this.selectedDelivery.id);
  }

  pushById(id: number) {
    this.deliveryHttpService.push(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.getAll();
          this.toastService.success(`Delivery was pushed successfully!`);
        },
        error: this.toastService.handleHttpError
      });
  }

  private confirmDecline() {
    this.confirmDeclineById(this.selectedDelivery.id);
  }

  confirmDeclineById(id: number) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to DECLINE this delivery?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-warn',
      accept: () => this.declineById(id)
    })
  }

  declineById(id: number) {
    this.deliveryHttpService.decline(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.getAll();
          this.toastService.success(`Delivery was declined successfully!`);
        },
        error: this.toastService.handleHttpError
      });
  }
}
