import { Component, DestroyRef, inject } from '@angular/core';
import { PageDto } from "../../../api/models/page-dto";
import { TransporterDto } from "../../../api/models/transporter-dto";
import { debounce, distinctUntilChanged, finalize, interval, Subject } from "rxjs";
import { TableLazyLoadEvent } from "primeng/table";
import { FilterMatchMode, FilterMetadata } from "primeng/api";
import { PrimengTableFilterCustomMatchMode } from "../../../models/primeng-table-filter-custom-match-mode";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Pageable } from "../../../api/models/pageable";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
import { TransporterHttpService } from "../../../api/services/transporter-http.service";
import { primeNgTableFiltersToRequestParams } from "../../../helpers/PrimeNgHelper";
import { ToastService } from "../../../services/toast.service";


@Component({
  selector: 'app-transporter-list',
  templateUrl: './transporter-list.component.html',
  styleUrls: ['./transporter-list.component.scss']
})
export class TransporterListComponent {
  private readonly destroyRef = inject(DestroyRef);
  public readonly MIN_CAPACITY_LOAD: number = 2000;
  public readonly MAX_CAPACITY_LOAD: number = 30000;
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  transporterPage?: PageDto<TransporterDto>;
  isLoading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  loadCapacityRangeValues: number[] = [this.MIN_CAPACITY_LOAD, this.MAX_CAPACITY_LOAD];

  constructor(private transporterHttpService: TransporterHttpService,
              private toastService: ToastService
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
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.isLoading = true;
    this.transporterPage = undefined;
    this.tableLazyLoad$.next(event);
  }

  private getAll(filters: any = {}, pageable: Pageable = new Pageable()){
    this.transporterHttpService.getAll(filters, pageable)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: page => this.transporterPage = page,
        error: this.toastService.handleHttpError
      });
  }

  deleteTransporter(id: number) {
    this.isLoading = true;
    this.transporterHttpService.delete(id)
      .pipe(
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: () => this.getAll(),
        error: this.toastService.handleHttpError
      });
  }
}
