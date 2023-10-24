import { WarehouseDto } from "../api/models/warehouse-dto";
import { TransporterDto } from "../api/models/transporter-dto";


export interface DeliveryData {
  warehouse_from?: WarehouseDto;
  warehouse_to?: WarehouseDto;
  transporter?: TransporterDto;
}
