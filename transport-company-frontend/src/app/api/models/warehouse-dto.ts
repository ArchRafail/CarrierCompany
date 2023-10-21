import { AddressDto } from "./address-dto";


export interface WarehouseDto {
  id: number,
  title: string,
  address: AddressDto
}
