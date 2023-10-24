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
import { DeliveryData } from "../../../models/delivery-data";


@Component({
  selector: 'app-delivery-item',
  templateUrl: './delivery-item.component.html',
  styleUrls: ['./delivery-item.component.scss']
})
export class DeliveryItemComponent {
  private readonly destroyRef = inject(DestroyRef);
  deliveryDto: DeliveryDto = {
    id: 0,
    warehouse_from: undefined,
    warehouse_to: undefined,
    transporter: undefined,
    cargo_name: "",
    cargo_amount: 0,
    status: DeliveryStatus.CREATED
  };
  submitDisable = false;
  isLoading = false;
  warehouses: WarehouseDto[] = [];
  transporters: TransporterDto[] = [];
  statuses: DeliveryStatus[] = Object.values(DeliveryStatus);
  selectedDeliveryData: DeliveryData = {warehouse_from: undefined, warehouse_to: undefined, transporter: undefined};

  constructor(private route: ActivatedRoute,
              private deliveryHttpService: DeliveryHttpService,
              private transporterHttpService: TransporterHttpService,
              private warehouseHttpService: WarehouseHttpService,
              private router: Router,
              private toastService: ToastService) {
    let {id} = route.snapshot.params;

    if(id) {
      this.getDelivery(id);
    } else {
      this.isLoading = false;
    }
    this.getWarehouses();
    this.getTransporters();
  }

  getDelivery(id: number) {
    this.isLoading = true;
    this.deliveryHttpService.get(id)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: deliveryDto => {
          this.deliveryDto = deliveryDto;
          this.getDeliveryData().then(deliveryData => this.selectedDeliveryData = deliveryData);
          },
        error: this.toastService.handleHttpError
      })
  }

  getWarehouses() {
    this.warehouseHttpService.getList()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: warehousesDto => this.warehouses = warehousesDto,
        error: this.toastService.handleHttpError
      })
  }

  getTransporters() {
    this.transporterHttpService.getList()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: transportersDto => this.transporters = transportersDto,
        error: this.toastService.handleHttpError
      })
  }

  onClickSubmit() {
    this.submitDisable = true;
    this.deliveryDto.warehouse_from = this.selectedDeliveryData.warehouse_from;
    this.deliveryDto.warehouse_to = this.selectedDeliveryData.warehouse_to;
    this.deliveryDto.transporter = this.selectedDeliveryData.transporter;
    if(this.deliveryDto.id) {
      this.deliveryHttpService.update(this.deliveryDto)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: () => {
            this.toastService.success(`Delivery was updated successfully!`);
            this.router.navigate(["/deliveries"]);
          },
          error: this.toastService.handleHttpError
        });
    } else {
      this.deliveryHttpService.create(this.deliveryDto)
        .pipe(takeUntilDestroyed(this.destroyRef))
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

  async getDeliveryData() : Promise<DeliveryData> {
    return {
      warehouse_from: this.deliveryDto.warehouse_from!,
      warehouse_to: this.deliveryDto.warehouse_to!,
      transporter: this.deliveryDto.transporter!
    };
  }


}
