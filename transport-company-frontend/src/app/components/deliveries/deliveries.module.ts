import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryListComponent } from './delivery-list/delivery-list.component';
import { DeliveryItemComponent } from './delivery-item/delivery-item.component';
import { RouterModule } from "@angular/router";


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
    CommonModule
  ]
})
export class DeliveriesModule { }
