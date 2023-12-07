import { Component, DestroyRef, inject } from '@angular/core';
import { CredentialsDto } from "../../../api/models/credentials-dto";
import { Router } from "@angular/router";
import { ToastService } from "../../../services/toast.service";
import { finalize } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AuthService } from "../../../services/auth.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  private readonly destroyRef = inject(DestroyRef);
  credentials: CredentialsDto = {
    email: "",
    password: "",
    device_id: ""
  };
  showErrorMessage = false;
  loading: boolean = false;

  constructor(private authService: AuthService, private router: Router, private toastService: ToastService) { }

  login(): void {
    this.loading = true;
    this.showErrorMessage = false;
    this.authService.login(this.credentials)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef))
      .subscribe({
          next: () => this.router.navigate(['/']),
          error: error => {
            if (error.status != 401) {
              this.toastService.handleHttpError(error);
            } else {
              this.showErrorMessage = true;
            }
          }
        }
      );
  }
}
