import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { API } from "../../constants/constants";
import { Pageable } from "../models/pageable";
import { PageDto } from "../models/page-dto";
import { WarehouseDto } from "../models/warehouse-dto";


@Injectable({
  providedIn: 'root'
})
export class WarehouseHttpService {
  private readonly URL = `${API}/warehouses`;

  constructor(private http: HttpClient) { }

  getAll(filters: any = {}, pageable: Pageable) {
    let params: HttpParams = new HttpParams({fromObject: {...filters, ...pageable}});
    return this.http.get<PageDto<WarehouseDto>>(this.URL, { params });
  }

  get(id: number) {
    return this.http.get<WarehouseDto>(`${this.URL}/${id}`);
  }

  create(warehouseDto: WarehouseDto) {
    return this.http.post<WarehouseDto>(this.URL, warehouseDto);
  }

  update(warehouseDto: WarehouseDto) {
    return this.http.put<WarehouseDto>(`${this.URL}/${warehouseDto.id}`, warehouseDto);
  }

  delete(id: number) {
    return this.http.delete<WarehouseDto>(`${this.URL}/${id}`);
  }

  getList() {
    return this.http.get<WarehouseDto[]>(`${this.URL}/list`);
  }
}
