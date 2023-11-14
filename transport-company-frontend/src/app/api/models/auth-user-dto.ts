import { Role } from "./role";


export interface AuthUserDto {
  id: number
  email: string
  first_name: string
  last_name: string
  role: Role
}
