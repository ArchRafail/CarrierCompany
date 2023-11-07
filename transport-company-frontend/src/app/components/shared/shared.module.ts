import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BooleanIconComponent } from './boolean-icon/boolean-icon.component';


@NgModule({
  declarations: [
    BooleanIconComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    BooleanIconComponent
  ]
})
export class SharedModule { }
