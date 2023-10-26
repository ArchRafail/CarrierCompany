import { FilterMetadata } from "primeng/api";
import { PrimengTableFilterCustomMatchMode } from "../models/primeng-table-filter-custom-match-mode";


export function primeNgTableFiltersToRequestParams(filters?: {
  [s: string]: FilterMetadata | FilterMetadata[] | undefined
}): any {
  let params: any = {};
  if (!filters) return params;
  Object.entries(filters).forEach(([key, filterMetadata]) => {
    const filter = filterMetadata as FilterMetadata;
    const value = filter?.value;
    if (!value) return;
    if (filter.matchMode === PrimengTableFilterCustomMatchMode.RANGE) {
      if ((value[0] || value[0] == 0) && value[1]) {
        params[key + "_from"] = value[0];
        params[key + "_to"] = value[1];
      }
    } else {
      params[key] = value;
    }
  })
  return params;
}
