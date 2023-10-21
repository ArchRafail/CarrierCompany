import { FilterMatchMode } from "primeng/api";
import { PrimengTableFilterCustomMatchMode } from "../models/primeng-table-filter-custom-match-mode";


export const PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE = [
  PrimengTableFilterCustomMatchMode.EQUALS_TEXT,
  PrimengTableFilterCustomMatchMode.WEIGHT_RANGE,
  PrimengTableFilterCustomMatchMode.LOCATION_RANGE,
  FilterMatchMode.STARTS_WITH,
  FilterMatchMode.CONTAINS,
]
