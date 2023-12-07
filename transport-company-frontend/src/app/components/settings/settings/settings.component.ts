import { Component, DestroyRef, inject } from '@angular/core';
import { AuthService } from "../../../services/auth.service";
import { ConfirmationService } from "primeng/api";
import { TokenHttpService } from "../../../api/services/token-http.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { ToastService } from "../../../services/toast.service";
import { NgForm } from "@angular/forms";
import { UserHttpService } from "../../../api/services/user-http.service";


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent {
  private readonly destroyRef = inject(DestroyRef);
  oldPassword?: string;
  newPassword?: string;
  repeatNewPassword?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  userEmail?: string;

  savingPassword = false;
  savingPersonalInfo = false;

  constructor(protected authService: AuthService,
              private confirmationService: ConfirmationService,
              private tokenHttpService: TokenHttpService,
              private toastService: ToastService,
              private userHttpService: UserHttpService) {
  }

  onClickPasswordSubmit(formPassword: NgForm) {
    this.savingPassword = true;
    let passwordUpdateDto = {old_password: this.oldPassword, new_password: this.newPassword};
    this.userHttpService.changePassword(this.authService.getUserId(), passwordUpdateDto)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.toastService.success(`Password was changed successfully!`);
          this.savingPassword = false;
          formPassword.resetForm();
        },
        error: this.toastService.handleHttpError
      });
  }

  onClickPersonalInfoSubmit(formUser: NgForm) {
    this.savingPersonalInfo = true;
    let userUpdateDto = {email: this.email, first_name: this.firstName, last_name: this.lastName};
    this.userHttpService.update(this.authService.getUserId(), userUpdateDto)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.toastService.success(`User info was changed successfully!`);
          this.savingPersonalInfo = false;
          formUser.resetForm();
        },
        error: this.toastService.handleHttpError
      });
  }

  confirmClearMyTokens() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to remove your access on all devices?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.clearMyTokens()
    });
  }

  confirmClearUserTokens() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to remove user access on all devices?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.clearUserTokens()
    });
  }

  confirmClearAllTokens() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to remove ALL access on ALL devices?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.secondConfirmationClearAllTokens()
    });
  }

  secondConfirmationClearAllTokens() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to remove access from ALL users? This action is irreversible!!',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.clearAllTokens()
    });
  }

  clearMyTokens() {
    this.tokenHttpService.clearMyTokens()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.toastService.success(`Your access on all devices was removed successfully!`),
        error: this.toastService.handleHttpError
      });
  }

  clearUserTokens() {
    if (!this.userEmail) {
      this.toastService.warning(`User email is not specified!`);
      return;
    }
    this.tokenHttpService.clearUserTokens(this.userEmail)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.toastService.success(`User's access on all devices was removed successfully!`),
        error: this.toastService.handleHttpError
      })
  }

  clearAllTokens() {
    this.tokenHttpService.clearAllTokens(this.authService.getMyDeviceId())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.toastService.success(`All access on all devices was removed successfully!`),
        error: this.toastService.handleHttpError
      });
  }
}
