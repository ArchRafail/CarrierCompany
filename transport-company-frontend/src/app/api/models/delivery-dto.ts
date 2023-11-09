import { WarehouseDto } from "./warehouse-dto";
import { TransporterDto } from "./transporter-dto";
import { DeliveryStatus } from "./delivery-status";


export interface DeliveryDto {
  id: number;
  warehouse_from?: WarehouseDto;
  warehouse_to?: WarehouseDto;
  transporter?: TransporterDto;
  cargo_name: string;
  cargo_amount: number;
  status: DeliveryStatus;
  created?: string;
  scheduled?: string;
  actual?: string;
}
