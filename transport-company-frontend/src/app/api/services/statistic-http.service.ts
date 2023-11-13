import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { API } from "../../constants/constants";
import { DeliveriesStatusStructuralDto } from "../models/deliveries-status-structural-dto";
import { DeliveriesOnTimeStructuralDto } from "../models/deliveries-on-time-structural-dto";


@Injectable({
  providedIn: 'root'
})
export class StatisticHttpService {
  private readonly URL = `${API}/statistics`;

  constructor(private http: HttpClient) { }

  getDeliveriesStatusesStructuralStatistic() {
    return this.http.get<DeliveriesStatusStructuralDto[]>(`${this.URL}/deliveries-statuses`);
  }

  getDeliveriesOnTimeStructuralStatistic() {
    return this.http.get<DeliveriesOnTimeStructuralDto[]>(`${this.URL}/deliveries-on-time`);
  }
}
