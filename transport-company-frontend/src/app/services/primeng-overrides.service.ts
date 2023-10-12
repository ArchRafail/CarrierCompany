import { Injectable } from '@angular/core';
import { ColumnFilterFormElement } from "primeng/table";


@Injectable({
  providedIn: 'root'
})
export class PrimengOverridesService {

  init() {
    this.overrideTableColumnFilter();
  }

  overrideTableColumnFilter() {
    ColumnFilterFormElement.prototype.onModelChange = function (value) {
      this.filterConstraint!.value = value;
      if (this.type || value === '') {
        this.dt._filter();
      }
    }
  }
}
