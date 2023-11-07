import { TableLazyLoadEvent } from "primeng/table";
import { camelCase } from "change-case";


export class Pageable {
  page?: number;
  size?: number;
  sort?: string;

  constructor(page: number = 0, size: number = 10, sort: string = "") {
    this.page = page;
    this.size = size;
    this.sort = sort;
  }

  static fromPrimeNg(event: TableLazyLoadEvent) {
    let pageable = new Pageable();
    pageable.page = event.first! / event.rows!;
    pageable.size = event.rows!;
    if (event.sortField) {
      const sortOrder = event.sortOrder! > 0 ? SortOrder.ASC : SortOrder.DESC;
      let sortField = (event.sortField as string);
      if (sortField.includes(".")) {
        sortField = sortField.split(".").map(substring => camelCase(substring)).join(".");
      } else {
        sortField = camelCase(sortField);
      }
      pageable.sort = `${sortField},${sortOrder}`;
    }
    return pageable;
  }

  static allItems() {
    return new Pageable(0, 2147483647);
  }

  nextPage(): Pageable {
    this.page! += 1;
    return this;
  }

  mutate(page?: any, size?: any, sort?: any) {
    if (page !== null) this.page = page;
    if (size !== null) this.size = size;
    if (sort !== null) this.sort = sort;
    return this
  }

  mutatePage(page: any) {
    return this.mutate(page)
  }

  mutateSize(size: any) {
    return this.mutate(undefined, size)
  }

  mutateSort(sort: any) {
    return this.mutate(undefined, undefined, sort)
  }
}

export enum SortOrder {
  ASC = "ASC",
  DESC = "DESC"
}
