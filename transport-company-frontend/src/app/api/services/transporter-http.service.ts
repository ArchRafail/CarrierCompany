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
}
