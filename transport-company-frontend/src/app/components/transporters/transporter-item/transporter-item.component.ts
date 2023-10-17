import { Component, DestroyRef, inject } from '@angular/core';
import { TransporterDto } from "../../../api/models/transporter-dto";
import { ActivatedRoute, Router } from "@angular/router";
import { TransporterHttpService } from "../../../api/services/transporter-http.service";
import { ToastService } from "../../../services/toast.service";
import { finalize } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";

@Component({
  selector: 'app-transporter-item',
  templateUrl: './transporter-item.component.html',
  styleUrls: ['./transporter-item.component.scss']
})
export class TransporterItemComponent {
  private readonly destroyRef = inject(DestroyRef);
  transporterDto: TransporterDto = {
    id: 0,
    name: "",
    car_model: "",
    load_capacity: 0
  };
  submitDisable = false;
  isLoading = false;

  constructor(private route: ActivatedRoute,
              private transporterHttpService: TransporterHttpService,
              private router: Router,
              private toastService: ToastService) {
    let {id} = route.snapshot.params;

    if(id) {
      this.getTransporter(id);
    } else {
      this.isLoading = false;
    }
  }

  getTransporter(id: number) {
    this.isLoading = true;
    this.transporterHttpService.get(id)
      .pipe(
        finalize(() => this.isLoading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: transporterDto => {
          this.transporterDto = transporterDto;
        },
        error: this.toastService.handleHttpError
    })
  }

  onClickSubmit() {
    this.submitDisable = true;
    if(this.transporterDto.id) {
      this.transporterHttpService.update(this.transporterDto).subscribe({
        next: () => this.router.navigate([""]),
        error: this.toastService.handleHttpError
      });
    } else {
      this.transporterHttpService.create(this.transporterDto).subscribe({
        next: () => this.router.navigate([""]),
        error: this.toastService.handleHttpError
      });
    }
  }

  toBoolean(value: boolean | null) :boolean {
    return value === true;
  }
}
