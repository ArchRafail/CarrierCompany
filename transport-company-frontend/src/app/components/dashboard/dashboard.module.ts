import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { NgChartsModule } from "ng2-charts";
import { ChartModule } from "primeng/chart";


@NgModule({
  declarations: [
    DashboardComponent,
  ],
  imports: [
    RouterModule.forChild([
      {path: '', component: DashboardComponent},
    ]),
    CommonModule,
    NgChartsModule,
    ChartModule,
  ]
})
export class DashboardModule { }
