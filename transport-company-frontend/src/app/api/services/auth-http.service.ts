import { Injectable } from '@angular/core';
import { API } from "../../constants/constants";
import { HttpClient } from "@angular/common/http";
import { CredentialsDto } from "../models/credentials-dto";
import { LoginResponse } from "../models/login-response";
import { TokenResponse } from "../models/token-response";


@Injectable({
  providedIn: 'root'
})
export class AuthHttpService {
  private readonly URL = `${API}/auth`;

  constructor(private http: HttpClient) { }

  login(credentials: CredentialsDto) {
    return this.http.post<LoginResponse>(`${this.URL}/login`, credentials);
  }

  refreshToken(deviceId: string) {
    return this.http.post<TokenResponse>(`${this.URL}/refreshtoken`, deviceId);
  }
}
