import { Pageable } from "./pageable";


export class PageDto<T> {
  content: T[];
  pageable: {
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    }
    offset: number;
    page_number: number;
    page_size: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  total_pages: number;
  total_elements: number;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  first: boolean;
  number_of_elements: number;
  empty: boolean;

  constructor(content: T[], pageable: Pageable){
    this.pageable = {
      sort: {
        empty: content.length === 0,
        sorted: pageable?.sort !== undefined,
        unsorted: pageable?.sort === undefined
      },
      offset: pageable?.page ?? 0,
      page_number: pageable?.page ?? 0,
      page_size: pageable?.size || 10,
      paged: true,
      unpaged: false
    };
    this.size = this.pageable.page_size;
    this.number = this.pageable.page_number;
    this.last = this.number + 1 >= this.size;
    this.total_pages = Math.ceil( content.length / this.size);
    this.total_elements = content.length;
    this.sort = {
      empty: false,
      sorted: false,
      unsorted: false
    };
    this.first = this.number === 0;
    this.number_of_elements = content.length;
    this.empty = content.length === 0;

    let fromIndex = this.number * this.size;
    let toIndex = Math.min(fromIndex + this.size, content.length);
    this.content = content.slice(fromIndex, toIndex);
  }
}
