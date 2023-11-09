import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { StatisticHttpService } from "../../api/services/statistic-http.service";
import DatalabelsPlugin from 'chartjs-plugin-datalabels';
import { ChartOptions, ChartData } from 'chart.js';
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { finalize } from "rxjs";
import { TitleCasePipe } from "@angular/common";
import { ToastService } from "../../services/toast.service";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [TitleCasePipe]
})
export class DashboardComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  public loading: boolean = false;

  public pieChartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
      datalabels: {
        formatter: (value: any, ctx: any) => {
          let label;
          if (ctx.chart.data.labels) {
            label = ctx.chart.data.labels[ctx.dataIndex];
          }
          return label;
        },
        color: '#fff',
      }
    }
  };

  public pieChartData: ChartData<'pie'> = {
    labels: [],
    datasets: [{
      data: [],
      backgroundColor: ['#f99baf', '#80c1ed', '#f9dc94', '#76db9b', '#2690a1']
    }]
  }

  public pieChartPlugins = [DatalabelsPlugin];

  constructor(private statisticHttpService: StatisticHttpService,
              private titleCasePipe: TitleCasePipe,
              private toastService: ToastService) { }

  ngOnInit() {
    this.loading = true;
    this.statisticHttpService.getDeliveriesStatusesStructuralStatistic()
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: values => {
          const labels: String[] = [];
          const quantities: number[] = [];
          for (const value of values) {
            let deliveryStatus: String = this.titleCasePipe.transform(value.delivery_status.toString().toLowerCase());
            labels.push(deliveryStatus);
            quantities.push(value.quantity);
          }
          this.pieChartData.labels = labels;
          this.pieChartData.datasets[0].data = quantities;
        },
        error: this.toastService.handleHttpError
      });
  }
}
