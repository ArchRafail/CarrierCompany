import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SettingsComponent } from './settings/settings.component';
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { UsersTableComponent } from './users-table/users-table.component';
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { DropdownModule } from "primeng/dropdown";
import { SharedModule } from "../shared/shared.module";
import { SliderModule } from "primeng/slider";
import { TableModule } from "primeng/table";
import { ContextMenuModule } from "primeng/contextmenu";
import { ValidatorsModule } from "../../validators/validators.module";
import { NgxErrorsModule } from "@ngspot/ngx-errors";
import { ChangeRoleDialogComponent } from './change-role-dialog/change-role-dialog.component';
import { DialogService } from "primeng/dynamicdialog";


@NgModule({
  declarations: [
    SettingsComponent,
    UsersTableComponent,
    ChangeRoleDialogComponent
  ],
  imports: [
    RouterModule.forChild([
      {path: '', component: SettingsComponent}
    ]),
    CommonModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    ConfirmDialogModule,
    DropdownModule,
    SharedModule,
    SliderModule,
    TableModule,
    ContextMenuModule,
    ValidatorsModule,
    NgxErrorsModule
  ],
  providers: [
    DialogService
  ]
})
export class SettingsModule { }
