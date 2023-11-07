import { Component, DestroyRef, inject } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ToastService } from "../../../services/toast.service";
import { finalize } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { WarehouseDto } from "../../../api/models/warehouse-dto";
import { WarehouseHttpService } from "../../../api/services/warehouse-http.service";
import { ACTIVATE_OPTIONS } from "../../../constants/constants";


@Component({
  selector: 'app-warehouse-item',
  templateUrl: './warehouse-item.component.html',
  styleUrls: ['./warehouse-item.component.scss']
})
export class WarehouseItemComponent {
  private readonly destroyRef = inject(DestroyRef);
  public readonly ACTIVATE_OPTIONS = ACTIVATE_OPTIONS;
  warehouseDto: WarehouseDto = {
    id: 0,
    title: "",
    address: {
      city: "",
      street: "",
      location: {
        latitude: 0,
        longitude: 0
      }
    },
    is_active: true
  };
  submitDisable = false;
  isLoading = false;

  constructor(private route: ActivatedRoute,
              private warehouseHttpService: WarehouseHttpService,
              private router: Router,
              private toastService: ToastService) {
    let {id} = route.snapshot.params;

    if(id) {
      this.getWarehouse(id);
    } else {
      this.isLoading = false;
    }
  }

  getWarehouse(id: number) {
    this.isLoading = true;
    this.warehouseHttpService.get(id)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: warehouseDto => this.warehouseDto = warehouseDto,
        error: this.toastService.handleHttpError
      })
  }

  onClickSubmit() {
    this.submitDisable = true;
    if(this.warehouseDto.id) {
      this.warehouseHttpService.update(this.warehouseDto)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
        next: () => {
          this.toastService.success(`Warehouse was updated successfully!`);
          this.router.navigate(["/warehouses"]);
        },
        error: this.toastService.handleHttpError
      });
    } else {
      this.warehouseHttpService.create(this.warehouseDto)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
        next: () => {
          this.toastService.success(`Warehouse was created successfully!`);
          this.router.navigate(["/warehouses"]);
        },
        error: this.toastService.handleHttpError
      });
    }
  }

  toBoolean(value: boolean | null) :boolean {
    return value === true;
  }
}
