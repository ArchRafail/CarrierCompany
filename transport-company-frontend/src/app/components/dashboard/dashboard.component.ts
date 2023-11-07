import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { StatisticHttpService } from "../../api/services/statistic-http.service";
import { ChartOptions } from "chart.js";
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

  public pieChartOptions: ChartOptions<'pie'> = { responsive: false };
  public pieChartLabels: String[] = [];
  public pieChartDatasets: {data: number[]}[] = [ {
    data: []
  } ];
  public pieChartLegend = true;
  public pieChartPlugins = [];

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
          this.pieChartLabels = labels;
          this.pieChartDatasets[0].data = quantities;
        },
        error: this.toastService.handleHttpError
      });
  }
}
