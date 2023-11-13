import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { API } from "../../constants/constants";
import { DeliveriesStatusStructuralDto } from "../models/deliveries-status-structural-dto";


@Injectable({
  providedIn: 'root'
})
export class StatisticHttpService {
  private readonly URL = `${API}/statistics`;

  constructor(private http: HttpClient) { }

  getDeliveriesStructuralStatistic() {
    return this.http.get<DeliveriesStatusStructuralDto[]>(`${this.URL}/deliveries-statuses`);
  }
}
