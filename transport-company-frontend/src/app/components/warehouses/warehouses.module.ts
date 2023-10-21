import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { WarehouseItemComponent } from './warehouse-item/warehouse-item.component';
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ContextMenuModule } from "primeng/contextmenu";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "primeng/api";
import { SliderModule } from "primeng/slider";
import { TableModule } from "primeng/table";


@NgModule({
  declarations: [
    WarehouseListComponent,
    WarehouseItemComponent
  ],
  imports: [
    RouterModule.forChild([
      {path: '', component: WarehouseListComponent},
      {path: 'item', component: WarehouseItemComponent},
      {path: 'item/:id', component: WarehouseItemComponent}
    ]),
    CommonModule,
    ButtonModule,
    ConfirmDialogModule,
    ContextMenuModule,
    InputTextModule,
    ReactiveFormsModule,
    SharedModule,
    SliderModule,
    TableModule,
    FormsModule
  ]
})
export class WarehousesModule { }
