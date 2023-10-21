import { Component, DestroyRef, inject } from '@angular/core';
import { PageDto } from "../../../api/models/page-dto";
import { debounce, distinctUntilChanged, finalize, interval, Subject } from "rxjs";
import { TableLazyLoadEvent } from "primeng/table";
import { ConfirmationService, FilterMatchMode, FilterMetadata, MenuItem } from "primeng/api";
import { ToastService } from "../../../services/toast.service";
import { Router } from "@angular/router";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
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
  public readonly MIN_LOCATION: number = 0;
  public readonly MAX_LOCATION: number = 100;
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  warehousePage?: PageDto<WarehouseDto>;
  isLoading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  latitudeRangeValues: number[] = [this.MIN_LOCATION, this.MAX_LOCATION];
  longitudeRangeValues: number[] = [this.MIN_LOCATION, this.MAX_LOCATION];
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
    this.contextMenuItems = [
      {
        label: 'Edit',
        icon: 'pi pi-pencil',
        command: () => this.edit()
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
    this.router.navigate(['/warehouse/item/']);
  }

  deleteById(id: number) {
    this.isLoading = true;
    this.warehouseHttpService.delete(id)
      .pipe(
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: () => this.getAll(),
        error: this.toastService.handleHttpError
      });
  }

  edit() {
    this.router.navigate([`/warehouse/item/${this.selectedWarehouse.id}`]);
  }

  private confirmDelete() {
    this.confirmDeleteById(this.selectedWarehouse.id);
  }

  confirmDeleteById(id: number) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to DELETE this warehouse?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.deleteById(id)
    })
  }
}
