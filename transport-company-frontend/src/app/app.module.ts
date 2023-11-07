import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AppRoutingModule } from "./app-routing.module";
import { LayoutComponent } from './layout/layout.component';
import { LayoutHeaderComponent } from './layout/layout-header/layout-header.component';
import { TabMenuModule } from "primeng/tabmenu";
import { AvatarModule } from "primeng/avatar";
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ButtonModule } from "primeng/button";
import { HttpClientModule } from "@angular/common/http";
import { ConfirmationService, MessageService } from "primeng/api";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastModule } from "primeng/toast";
import { PageUnderConstructionComponent } from './components/page-under-construction/page-under-construction.component';
import { NgChartsModule } from "ng2-charts";


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LayoutComponent,
    LayoutHeaderComponent,
    PageNotFoundComponent,
    PageUnderConstructionComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    TabMenuModule,
    AvatarModule,
    ButtonModule,
    HttpClientModule,
    ToastModule,
    NgChartsModule,
  ],
  providers: [
    MessageService,
    ConfirmationService,
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
