import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransporterListComponent } from './transporter-list/transporter-list.component';
import { RouterModule } from "@angular/router";
import { TransporterItemComponent } from './transporter-item/transporter-item.component';
import { TableModule } from "primeng/table";
import { SliderModule } from "primeng/slider";
import { FormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";


@NgModule({
  declarations: [
    TransporterListComponent,
    TransporterItemComponent
  ],
    imports: [
        RouterModule.forChild([
            {path: '', component: TransporterListComponent},
            {path: 'item', component: TransporterItemComponent},
            {path: 'item/:id', component: TransporterItemComponent}
        ]),
        CommonModule,
        TableModule,
        SliderModule,
        FormsModule,
        InputTextModule,
        ButtonModule
    ]
})
export class TransportersModule { }
