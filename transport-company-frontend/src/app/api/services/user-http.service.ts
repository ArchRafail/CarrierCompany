import { Injectable } from '@angular/core';
import { API } from "../../constants/constants";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Pageable } from "../models/pageable";
import { PageDto } from "../models/page-dto";
import { UserDto } from "../models/user-dto";
import { PasswordUpdateDto } from "../models/password-update-dto";
import { UserUpdateDto } from "../models/user-update-dto";
import { Role } from "../models/role";


@Injectable({
  providedIn: 'root'
})
export class UserHttpService {
  private readonly URL = `${API}/users`;

  constructor(private http: HttpClient) { }

  getAll(filters: any = {}, pageable: Pageable) {
    let params: HttpParams = new HttpParams({fromObject: {...filters, ...pageable}});
    return this.http.get<PageDto<UserDto>>(this.URL, { params });
  }

  changePassword(id: number, passwordUpdateDto: PasswordUpdateDto) {
    return this.http.put<UserDto>(`${this.URL}/${id}/password`, passwordUpdateDto);
  }

  update(id: number, userUpdateDto: UserUpdateDto) {
    return this.http.put<UserDto>(`${this.URL}/${id}`, userUpdateDto);
  }

  updateActive(id: number, isActive: boolean) {
    return this.http.put<UserDto>(`${this.URL}/${id}/active`, { value: isActive });
  }

  changeRole(id: number, role: Role) {
    return this.http.put<UserDto>(`${this.URL}/${id}/role?role=${role}`, null);
  }
}
