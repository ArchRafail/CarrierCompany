import { FilterMetadata } from "primeng/api";
import { PrimengTableFilterCustomMatchMode } from "../models/primeng-table-filter-custom-match-mode";
import { DateTime } from "luxon";
import { LUXON_DATE_TIME_ZONED_FORMAT } from "../constants/constants";


export function primeNgTableFiltersToRequestParams(filters?: {
  [s: string]: FilterMetadata | FilterMetadata[] | undefined
}): any {
  let params: any = {};
  if (!filters) return params;
  Object.entries(filters).forEach(([key, filterMetadata]) => {
    const filter = filterMetadata as FilterMetadata;
    const value = filter?.value;
    if (value == null) return;
    if (filter.matchMode === PrimengTableFilterCustomMatchMode.RANGE) {
      if ((value[0] || value[0] == 0) && value[1]) {
        params[key + "_from"] = value[0];
        params[key + "_to"] = value[1];
      }
    } else if (filter.matchMode === PrimengTableFilterCustomMatchMode.DATE_TIME_RANGE) {
      if (value[0] && value[1]) {
        params[key + "_from"] = DateTime.fromJSDate(value[0]).toFormat(LUXON_DATE_TIME_ZONED_FORMAT);
        params[key + "_to"] = DateTime.fromJSDate(value[1]).toFormat(LUXON_DATE_TIME_ZONED_FORMAT);
      }
    } else {
      params[key] = value;
    }
  })
  return params;
}
