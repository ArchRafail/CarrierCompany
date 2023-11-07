import { Component, DestroyRef, inject, ViewChild } from '@angular/core';
import { PageDto } from "../../../api/models/page-dto";
import { debounce, distinctUntilChanged, finalize, interval, Subject } from "rxjs";
import { Table, TableLazyLoadEvent } from "primeng/table";
import { ConfirmationService, FilterMatchMode, FilterMetadata, MenuItem } from "primeng/api";
import { ToastService } from "../../../services/toast.service";
import { Router } from "@angular/router";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { DROPDOWN_BOOL_OPTIONS, PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
import { Pageable } from "../../../api/models/pageable";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { WarehouseDto } from "../../../api/models/warehouse-dto";
import { PrimengTableFilterCustomMatchMode } from "../../../models/primeng-table-filter-custom-match-mode";
import { WarehouseHttpService } from "../../../api/services/warehouse-http.service";
import { primeNgTableFiltersToRequestParams } from "../../../helpers/PrimeNgHelper";


@Component({
  selector: 'app-warehouse-list',
  templateUrl: './warehouse-list.component.html',
  styleUrls: ['./warehouse-list.component.scss']
})
export class WarehouseListComponent {
  private readonly destroyRef = inject(DestroyRef);
  public readonly LOCATION_RANGE: number[] = [0, 100];
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  public readonly DROPDOWN_BOOL_OPTIONS = DROPDOWN_BOOL_OPTIONS;
  @ViewChild("table") private table!: Table;
  warehousePage?: PageDto<WarehouseDto>;
  isLoading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  latitudeRangeValues: number[] = [this.LOCATION_RANGE[0], this.LOCATION_RANGE[1]];
  longitudeRangeValues: number[] = [this.LOCATION_RANGE[0], this.LOCATION_RANGE[1]];
  contextMenuItems: MenuItem[] = [];
  selectedWarehouse!: WarehouseDto;

  constructor(private warehouseHttpService: WarehouseHttpService,
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
        visible: this.selectedWarehouse && !this.selectedWarehouse.is_active
      },
      {
        label: 'Deactivate',
        icon: 'pi pi-trash',
        command: () => this.confirmDeactivation(),
        visible: this.selectedWarehouse && this.selectedWarehouse.is_active,
        styleClass: "menuitem-danger"
      },
    ];
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.isLoading = true;
    this.warehousePage = undefined;
    this.tableLazyLoad$.next(event);
  }

  private getAll(filters: any = {}, pageable: Pageable = new Pageable()){
    this.warehouseHttpService.getAll(filters, pageable)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: page => this.warehousePage = page,
        error: this.toastService.handleHttpError
      });
  }

  addWarehouse() {
    this.router.navigate(['/warehouses/item/']);
  }

  edit() {
    this.router.navigate([`/warehouses/item/${this.selectedWarehouse.id}`]);
  }

  private updateActive(isActive: boolean) {
    this.isLoading = true;
    this.warehouseHttpService.updateActive(this.selectedWarehouse.id, isActive)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: () => {
          if (isActive) {
            this.toastService.success(`Warehouse was activated successfully!`);
          } else {
            this.toastService.success(`Warehouse was deactivated successfully!`);
          }
          this.table.onLazyLoad.emit(this.table.createLazyLoadMetadata());
        },
        error: this.toastService.handleHttpError
      });
  }

  private confirmDeactivation() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to DELETE this warehouse?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.updateActive(false)
    })
  }

  onContextMenuSelect(event: any) {
    this.initContextMenu();
  }
}
