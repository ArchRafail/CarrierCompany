import { AuthUserDto } from "./auth-user-dto";


export interface LoginResponse {
  user: AuthUserDto;
  access_token: string;
  refresh_token: string;
}
