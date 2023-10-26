import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryListComponent } from './delivery-list/delivery-list.component';
import { DeliveryItemComponent } from './delivery-item/delivery-item.component';
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { FormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ContextMenuModule } from "primeng/contextmenu";
import { SliderModule } from "primeng/slider";
import { TableModule } from "primeng/table";
import { DropdownModule } from "primeng/dropdown";
import { MenuModule } from "primeng/menu";
import { ToastModule } from "primeng/toast";


@NgModule({
  declarations: [
    DeliveryListComponent,
    DeliveryItemComponent
  ],
  imports: [
    RouterModule.forChild([
      {path: '', component: DeliveryListComponent},
      {path: 'item', component: DeliveryItemComponent},
      {path: 'item/:id', component: DeliveryItemComponent}
    ]),
    CommonModule,
    ButtonModule,
    FormsModule,
    InputTextModule,
    ConfirmDialogModule,
    ContextMenuModule,
    SliderModule,
    TableModule,
    DropdownModule,
    MenuModule,
    ToastModule
  ]
})
export class DeliveriesModule { }
