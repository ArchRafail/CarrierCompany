import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { WarehouseItemComponent } from './warehouse-item/warehouse-item.component';
import { RouterModule } from "@angular/router";


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
    CommonModule
  ]
})
export class WarehousesModule { }
