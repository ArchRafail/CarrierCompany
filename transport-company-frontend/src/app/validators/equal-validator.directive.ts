import { Attribute, Directive, forwardRef } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from "@angular/forms";


@Directive({
  selector: '[validateEqual][formControlName],[validateEqual][formControl],[validateEqual][ngModel]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => EqualValidator),
      multi: true
    }
  ]
})
export class EqualValidator implements Validator {
  private readonly ERROR_KEY = 'validateEqual';
  private readonly ERROR = {[this.ERROR_KEY]: true};

  constructor(@Attribute('validateEqual') public validateEqual: string,
              @Attribute('validationEqualReverse') public validationEqualReverse: string,
              @Attribute('validationEqualIgnoreEmpty') public validationEqualIgnoreEmpty: string) { }

  private get isReverse() {
    if (!this.validationEqualReverse) return false;
    return this.validationEqualReverse === 'true';
  }

  private get isIgnoreEmpty() {
    if (!this.validationEqualIgnoreEmpty) return true;
    return this.validationEqualIgnoreEmpty === 'true';
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
    let compareControl = control.root.get(this.validateEqual);
    if (!compareControl) {
      return null;
    }

    // value not equal
    if (compareControl && selfValue !== compareControl.value && !this.isReverse) {
      return {
        validateEqual: true
      };
    }

    // value equal and reverse
    if (compareControl && selfValue === compareControl.value && this.isReverse) {
      if (compareControl.errors && compareControl.errors[this.ERROR_KEY]) {
        delete compareControl.errors[this.ERROR_KEY];
        if (!Object.keys(compareControl.errors).length) compareControl.setErrors(null);
      }
    }

    // value not equal and reverse
    if (compareControl && selfValue !== compareControl.value && this.isReverse) {
      compareControl.setErrors(this.ERROR);
    }

    return null;
  }

}
