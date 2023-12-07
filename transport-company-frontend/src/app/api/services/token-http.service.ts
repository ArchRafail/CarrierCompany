import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { API } from "../../constants/constants";


@Injectable({
  providedIn: 'root'
})
export class TokenHttpService {
  private readonly URL = `${API}/token`;

  constructor(private http: HttpClient) { }

  clearMyTokens() {
    return this.http.post<void>(`${this.URL}/remove-my-tokens`, null);
  }

  clearUserTokens(username: string) {
    return this.http.post<void>(`${this.URL}/remove-user-tokens`, username);
  }

  clearAllTokens(deviceId: string) {
    return this.http.post<void>(`${this.URL}/remove-all-tokens`, deviceId);
  }
}
