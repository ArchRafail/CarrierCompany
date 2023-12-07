import { Role } from "./role";


export interface UserDto {
  id: number;
  email: string;
  first_name: string;
  last_name: string;
  role: Role;
  is_disabled: boolean;
}
