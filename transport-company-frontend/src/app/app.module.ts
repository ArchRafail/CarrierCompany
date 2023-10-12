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
import { MessageService } from "primeng/api";


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LayoutComponent,
    LayoutHeaderComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TabMenuModule,
    AvatarModule,
    ButtonModule,
    HttpClientModule,
  ],
  providers: [
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
