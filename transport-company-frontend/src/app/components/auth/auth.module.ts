import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from "./login/login.component";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { NgxErrorsModule } from "@ngspot/ngx-errors";
import { MessageModule } from "primeng/message";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";


@NgModule({
  declarations: [
    LoginComponent,
  ],
  imports: [
    RouterModule.forChild([
      {path: 'login', component: LoginComponent},
    ]),
    CommonModule,
    FormsModule,
    NgxErrorsModule,
    MessageModule,
    InputTextModule,
    ButtonModule,
  ]
})
export class AuthModule { }
