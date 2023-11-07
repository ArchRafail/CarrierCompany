import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { WarehouseItemComponent } from './warehouse-item/warehouse-item.component';
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ContextMenuModule } from "primeng/contextmenu";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule } from "@angular/forms";
import { SliderModule } from "primeng/slider";
import { TableModule } from "primeng/table";
import { DropdownModule } from "primeng/dropdown";
import { SharedModule } from "../shared/shared.module";


@NgModule({
  declarations: [
    WarehouseListComponent,
    WarehouseItemComponent,
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
    SliderModule,
    TableModule,
    FormsModule,
    DropdownModule,
    SharedModule,
  ]
})
export class WarehousesModule { }
