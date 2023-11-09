import { Component, DestroyRef, inject } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToastService } from "../../../services/toast.service";
import { finalize } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { DeliveryDto } from "../../../api/models/delivery-dto";
import { DeliveryStatus } from "../../../api/models/delivery-status";
import { DeliveryHttpService } from "../../../api/services/delivery-http.service";
import { WarehouseDto } from "../../../api/models/warehouse-dto";
import { TransporterDto } from "../../../api/models/transporter-dto";
import { TransporterHttpService } from "../../../api/services/transporter-http.service";
import { WarehouseHttpService } from "../../../api/services/warehouse-http.service";
import { Pageable } from "../../../api/models/pageable";
import { DateTime } from "luxon";
import { LUXON_DATE_TIME_ZONED_FORMAT } from "../../../constants/constants";


@Component({
  selector: 'app-delivery-item',
  templateUrl: './delivery-item.component.html',
  styleUrls: ['./delivery-item.component.scss']
})
export class DeliveryItemComponent {
  private readonly destroyRef = inject(DestroyRef);
  private readonly END_OF_DAY_TIME_IN_MILLIS = 86399000;
  public readonly MINIMUM_DATE = new Date();
  deliveryDto: DeliveryDto = {
    id: 0,
    warehouse_from: undefined,
    warehouse_to: undefined,
    transporter: undefined,
    cargo_name: "",
    cargo_amount: 0,
    status: DeliveryStatus.CREATED,
    created: undefined,
    scheduled: undefined,
    actual: undefined
  };
  saving = false;
  loading = false;
  filteredWarehousesFrom: WarehouseDto[] = [];
  filteredWarehousesTo: WarehouseDto[] = [];
  filteredTransporters: TransporterDto[] = [];
  statuses: DeliveryStatus[] = Object.values(DeliveryStatus).filter(ds => ds !== DeliveryStatus.DELIVERED && ds !== DeliveryStatus.DECLINED);
  warehousesFromLoading: boolean = false;
  warehousesToLoading: boolean = false;
  transportersLoading: boolean = false;
  scheduledDate?: Date = undefined;

  constructor(private route: ActivatedRoute,
              private deliveryHttpService: DeliveryHttpService,
              private transporterHttpService: TransporterHttpService,
              private warehouseHttpService: WarehouseHttpService,
              private router: Router,
              private toastService: ToastService) {
    let {id} = route.snapshot.params;

    if(id) {
      this.getDelivery(id);
    }
  }

  getDelivery(id: number) {
    this.loading = true;
    this.deliveryHttpService.get(id)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: deliveryDto => {
          this.deliveryDto = deliveryDto;
          this.scheduledDate = new Date(deliveryDto.scheduled!)
          },
        error: this.toastService.handleHttpError
      })
  }

  filterWarehousesFrom(searchTerm = '') {
    if (this.warehousesFromLoading) return;
    this.warehousesFromLoading = true;
    let params: any = {};
    if (searchTerm) params.searchTerm = searchTerm;
    this.warehouseHttpService.getOptions(params, Pageable.allItems().mutateSort("title,ASC"))
      .pipe(
        finalize(() => this.warehousesFromLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: items => this.filteredWarehousesFrom = items.content,
        error: this.toastService.handleHttpError
      })
  }

  filterWarehousesTo(searchTerm = '') {
    if (this.warehousesToLoading) return;
    this.warehousesToLoading = true;
    let params: any = {};
    if (searchTerm) params.searchTerm = searchTerm;
    this.warehouseHttpService.getOptions(params, Pageable.allItems().mutateSort("title,ASC"))
      .pipe(
        finalize(() => this.warehousesToLoading = false),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe({
        next: items => this.filteredWarehousesTo = items.content.filter(w => w.id !== this.deliveryDto.warehouse_from?.id),
        error: this.toastService.handleHttpError
      })
  }

  warehouseDropdownValueConverter(warehouse: WarehouseDto) {
    return warehouse?.id ? `${warehouse.title}, ${warehouse.address.city}` : ''
  }

  filterTransporters(searchTerm = '') {
    if (this.transportersLoading) return;
    this.transportersLoading = true;
    let params: any = {};
    if (searchTerm) params.searchTerm = searchTerm;
    this.transporterHttpService.getOptions(params, Pageable.allItems().mutateSort("name,ASC"))
      .pipe(
        finalize(() => this.transportersLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: items => this.filteredTransporters = items.content,
        error: this.toastService.handleHttpError
      })
  }

  transporterDropdownValueConverter(transporter: TransporterDto) {
    return transporter?.id ? `${transporter.name}` : ''
  }

  onClickSubmit() {
    this.saving = true;
    if (this.deliveryDto.created === undefined) {
      this.deliveryDto.created = DateTime.fromJSDate(new Date()).toFormat(LUXON_DATE_TIME_ZONED_FORMAT);
    }
    if (this.scheduledDate!.getHours() === 0) {
      this.deliveryDto.scheduled = DateTime.fromJSDate(new Date(this.scheduledDate!.getTime() + this.END_OF_DAY_TIME_IN_MILLIS)).toFormat(LUXON_DATE_TIME_ZONED_FORMAT);
    } else {
      this.deliveryDto.scheduled = DateTime.fromJSDate(this.scheduledDate!).toFormat(LUXON_DATE_TIME_ZONED_FORMAT);
    }
    if (this.deliveryDto.id) {
      this.deliveryHttpService.update(this.deliveryDto)
        .pipe(
          finalize(() => this.saving = false),
          takeUntilDestroyed(this.destroyRef)
        )
        .subscribe({
          next: () => {
            this.toastService.success(`Delivery was updated successfully!`);
            this.router.navigate(["/deliveries"]);
          },
          error: this.toastService.handleHttpError
        });
    } else {
      this.deliveryHttpService.create(this.deliveryDto)
        .pipe(
          finalize(() => this.saving = false),
          takeUntilDestroyed(this.destroyRef)
        )
        .subscribe({
          next: () => {
            this.toastService.success(`Delivery was created successfully!`);
            this.router.navigate(["/deliveries"]);
          },
          error: this.toastService.handleHttpError
        });
    }
  }

  toBoolean(value: boolean | null) :boolean {
    return value === true;
  }
}
