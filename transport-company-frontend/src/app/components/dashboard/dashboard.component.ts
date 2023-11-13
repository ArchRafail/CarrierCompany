import { Component, DestroyRef, inject, OnInit, ViewChild } from '@angular/core';
import { StatisticHttpService } from "../../api/services/statistic-http.service";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { finalize } from "rxjs";
import { TitleCasePipe } from "@angular/common";
import { ToastService } from "../../services/toast.service";
import { COLORS_MAP, DefaultOptionsPie } from "../../constants/statistic-constants";
import { DeliveriesStatusStructuralDto } from "../../api/models/deliveries-status-structural-dto";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [TitleCasePipe]
})
export class DashboardComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  @ViewChild("deliveryStructuralPie") deliveryStructuralPie!: any;
  plugin = [ChartDataLabels];
  public loading: boolean = false;

  public readonly defaultOptionsPie = DefaultOptionsPie;

  deliveryStructural: { labels: any[], datasets: any[] } = { labels: [], datasets: [] }

  constructor(private statisticHttpService: StatisticHttpService,
              private titleCasePipe: TitleCasePipe,
              private toastService: ToastService) { }

  ngOnInit() {
    this.getDeliveryStructural();
  }

  private getDeliveryStructural() {
    this.loading = true;
    this.statisticHttpService.getDeliveriesStructuralStatistic()
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (statistic: DeliveriesStatusStructuralDto[]) => {
          this.deliveryStructural.labels = statistic.map(item => this.titleCasePipe.transform(item.delivery_status.toString().toLowerCase()));
          this.deliveryStructural.datasets = [{
            data: statistic.map(item => item.quantity),
            backgroundColor: statistic.map(item => COLORS_MAP[item.delivery_status]),
          }];
          if (this.deliveryStructuralPie) this.deliveryStructuralPie.refresh();
        },
        error: this.toastService.handleHttpError
      });
  }
}
