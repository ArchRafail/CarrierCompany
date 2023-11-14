import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from "./app-routing.module";
import { LayoutComponent } from './layout/layout.component';
import { LayoutHeaderComponent } from './layout/layout-header/layout-header.component';
import { TabMenuModule } from "primeng/tabmenu";
import { AvatarModule } from "primeng/avatar";
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { ConfirmationService, MessageService } from "primeng/api";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastModule } from "primeng/toast";
import { PageUnderConstructionComponent } from './components/page-under-construction/page-under-construction.component';
import { AuthInterceptor } from "./interceptors/auth.interceptor";
import { MenuModule } from "primeng/menu";


@NgModule({
  declarations: [
    AppComponent,
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
    HttpClientModule,
    ToastModule,
    MenuModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    MessageService,
    ConfirmationService,
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
