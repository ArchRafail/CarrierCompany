import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { API } from "../../constants/constants";
import { Pageable } from "../models/pageable";
import { PageDto } from "../models/page-dto";
import { TransporterDto } from "../models/transporter-dto";


@Injectable({
  providedIn: 'root'
})
export class TransporterHttpService {
  private readonly URL = `${API}/transporters`;

  constructor(private http: HttpClient) { }

  getAll(filters: any = {}, pageable: Pageable) {
    let params: HttpParams = new HttpParams({fromObject: {...filters, ...pageable}});
    return this.http.get<PageDto<TransporterDto>>(this.URL, { params });
  }

  get(id: number) {
    return this.http.get<TransporterDto>(`${this.URL}/${id}`);
  }

  create(transporterDto: TransporterDto) {
    return this.http.post<TransporterDto>(this.URL, transporterDto);
  }

  update(transporterDto: TransporterDto) {
    return this.http.put<TransporterDto>(`${this.URL}/${transporterDto.id}`, transporterDto);
  }

  delete(id: number) {
    return this.http.delete<TransporterDto>(`${this.URL}/${id}`);
  }
}
