import { Attribute, Directive, forwardRef } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from "@angular/forms";


@Directive({
  selector: '[validateNotEqual][formControlName],[validateNotEqual][formControl],[validateNotEqual][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => NotEqualValidator), multi: true }
  ]
})
export class NotEqualValidator implements Validator {
  private readonly ERROR_KEY = 'validateNotEqual';
  private readonly ERROR = {[this.ERROR_KEY]: true};

  constructor(@Attribute('validateNotEqual') public validateNotEqual: string,
              @Attribute('validationNotEqualReverse') public validationNotEqualReverse: string,
              @Attribute('validationNotEqualIgnoreEmpty') public validationNotEqualIgnoreEmpty: string) {
  }

  private get isReverse() {
    if (!this.validationNotEqualReverse) return false;
    return this.validationNotEqualReverse === 'true';
  }

  private get isIgnoreEmpty() {
    if (!this.validationNotEqualIgnoreEmpty) return true;
    return this.validationNotEqualIgnoreEmpty === 'true';
  }

  validate(control: AbstractControl): ValidationErrors | null {
    // self value
    if (!control) {
      return null;
    }
    let selfValue = control.value;

    // ignore if value is empty
    if (this.isIgnoreEmpty && selfValue?.length === 0) {
      return null;
    }

    // control value
    let compareControl = control.root.get(this.validateNotEqual);
    if (!compareControl) {
      return null;
    }

    // value equal
    if (compareControl && selfValue === compareControl.value && !this.isReverse) {
      return {
        validateNotEqual: true
      };
    }

    // value not equal and reverse
    if (compareControl && selfValue !== compareControl.value && this.isReverse) {
      if (compareControl.errors && compareControl.errors[this.ERROR_KEY]) {
        delete compareControl.errors[this.ERROR_KEY];
        if (!Object.keys(compareControl.errors).length) compareControl.setErrors(null);
      }
    }

    // value equal and reverse
    if (compareControl && selfValue === compareControl.value && this.isReverse) {
      compareControl.setErrors(this.ERROR);
    }

    return null;
  }

}
