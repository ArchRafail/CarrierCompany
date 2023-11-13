import { Component, DestroyRef, inject, OnInit, ViewChild } from '@angular/core';
import { StatisticHttpService } from "../../api/services/statistic-http.service";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { finalize } from "rxjs";
import { ToastService } from "../../services/toast.service";
import { COLORS_MAP, DefaultOptionsPie } from "../../constants/statistic-constants";
import { DeliveriesStatusStructuralDto } from "../../api/models/deliveries-status-structural-dto";
import { DeliveriesOnTimeStructuralDto } from "../../api/models/deliveries-on-time-structural-dto";
import * as changeCase from "change-case";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  @ViewChild("statusesDeliveryStructuralPie") statusesDeliveryStructuralPie!: any;
  @ViewChild("onTimeDeliveryStructuralPie") onTimeDeliveryStructuralPie!: any;
  plugin = [ChartDataLabels];
  public loading: boolean = false;

  public readonly defaultOptionsPie = DefaultOptionsPie;

  statusesDeliveryStructural: { labels: any[], datasets: any[] } = { labels: [], datasets: [] }
  onTimeDeliveryStructural: { labels: any[], datasets: any[] } = { labels: [], datasets: [] }

  constructor(private statisticHttpService: StatisticHttpService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.getStatusesDeliveryStructural();
    this.getOnTimeDeliveryStructural();
  }

  private getStatusesDeliveryStructural() {
    this.loading = true;
    this.statisticHttpService.getDeliveriesStatusesStructuralStatistic()
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (statistic: DeliveriesStatusStructuralDto[]) => {
          this.statusesDeliveryStructural.labels = statistic.map(item => changeCase.sentenceCase(item.delivery_status.toString().toLowerCase()));
          this.statusesDeliveryStructural.datasets = [{
            data: statistic.map(item => item.quantity),
            backgroundColor: statistic.map(item => COLORS_MAP[item.delivery_status]),
          }];
          if (this.statusesDeliveryStructuralPie) this.statusesDeliveryStructuralPie.refresh();
        },
        error: this.toastService.handleHttpError
      });
  }

  private getOnTimeDeliveryStructural() {
    this.loading = true;
    this.statisticHttpService.getDeliveriesOnTimeStructuralStatistic()
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (statistic: DeliveriesOnTimeStructuralDto[]) => {
          this.onTimeDeliveryStructural.labels = statistic.map(item => changeCase.sentenceCase(item.on_time_status.toString().toLowerCase()));
          this.onTimeDeliveryStructural.datasets = [{
            data: statistic.map(item => item.quantity),
            backgroundColor: statistic.map(item => COLORS_MAP[item.on_time_status]),
          }];
          if (this.onTimeDeliveryStructuralPie) this.onTimeDeliveryStructuralPie.refresh();
        },
        error: this.toastService.handleHttpError
      });
  }
}
