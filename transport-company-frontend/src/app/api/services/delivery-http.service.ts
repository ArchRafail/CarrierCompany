import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { API } from "../../constants/constants";
import { Pageable } from "../models/pageable";
import { PageDto } from "../models/page-dto";
import { DeliveryDto } from "../models/delivery-dto";


@Injectable({
  providedIn: 'root'
})
export class DeliveryHttpService {
  private readonly URL = `${API}/deliveries`;

  constructor(private http: HttpClient) { }

  getAll(filters: any = {}, pageable: Pageable) {
    let params: HttpParams = new HttpParams({fromObject: {...filters, ...pageable}});
    return this.http.get<PageDto<DeliveryDto>>(this.URL, { params });
  }

  get(id: number) {
    return this.http.get<DeliveryDto>(`${this.URL}/${id}`);
  }

  create(deliveryDto: DeliveryDto) {
    return this.http.post<DeliveryDto>(this.URL, deliveryDto);
  }

  update(deliveryDto: DeliveryDto) {
    return this.http.put<DeliveryDto>(`${this.URL}/${deliveryDto.id}`, deliveryDto);
  }

  delete(id: number) {
    return this.http.delete<DeliveryDto>(`${this.URL}/${id}`);
  }

  push(id: number) {
    return this.http.put<DeliveryDto>(`${this.URL}/${id}/push`, null);
  }

  decline(id: number) {
    return this.http.put<DeliveryDto>(`${this.URL}/${id}/decline`, null);
  }
}
