import { AuthService } from "../services/auth.service";
import { inject } from "@angular/core";
import { Router } from "@angular/router";


export const notAuthenticatedGuard = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);
  return !authService.isAuthenticated() || router.createUrlTree(['']);
}
