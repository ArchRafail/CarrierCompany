import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotEqualValidator } from './not-equal-validator.directive';
import { EqualValidator } from './equal-validator.directive';



@NgModule({
  declarations: [
    NotEqualValidator,
    EqualValidator
  ],
  exports: [
    NotEqualValidator,
    EqualValidator
  ],
  imports: [
    CommonModule
  ]
})
export class ValidatorsModule { }
