import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { catchError, finalize, first, Observable, Subject, switchMap, throwError } from 'rxjs';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { API } from "../constants/constants";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly AUTHORIZATION_HEADER_KEY = "Authorization";
  private isRefreshing = false;
  private refreshToken$: Subject<string> = new Subject<string>();

  constructor(private authService: AuthService, private router: Router) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!req.url.startsWith(`${API}`)) {
      return next.handle(req);
    }

    if (req.url.endsWith("login") || req.url.endsWith("refreshtoken")) {
      return next.handle(req);
    }

    if (this.authService.isAccessTokenExpired() && !this.isRefreshing) {
      return this.refreshTokenProcess(req, next);
    } else {
      if (!this.isRefreshing) {
        return next.handle(this.enrichRequestByAccessToken(req)).pipe(
          catchError(requestError => {
            if (requestError.status == 401 && requestError.error.message == "Token is expired") {
              if (this.isRefreshing) {
                return this.refreshToken$.pipe(
                  first(),
                  switchMap(() => next.handle(this.enrichRequestByAccessToken(req)))
                );
              }
              return this.refreshTokenProcess(req, next);
            } else if (requestError.status == 401) {
              return this.logout(requestError);
            } else {
              return throwError(() => requestError);
            }
          }),
          finalize(() => this.isRefreshing = false)
        );
      } else {
        return this.refreshToken$.pipe(
          first(),
          switchMap(() => next.handle(this.enrichRequestByAccessToken(req)))
        );
      }
    }
  }

  private refreshTokenProcess(req: HttpRequest<any>, next: HttpHandler) {
    this.isRefreshing = true;
    return this.authService.refreshToken().pipe(
      switchMap(token => {
        this.isRefreshing = false;
        this.refreshToken$.next(token.access_token);
        return next.handle(this.enrichRequestByAccessToken(req));
      }),
      catchError(requestError => {
        this.isRefreshing = false;
        return this.logout(requestError, true);
      })
    );
  }

  private enrichRequestByAccessToken(request: HttpRequest<any>) {
    const accessToken = this.authService.getAccessToken();
    if (!accessToken) {
      return request;
    }
    return request.clone({
      headers: request.headers.set(this.AUTHORIZATION_HEADER_KEY, `Bearer ${accessToken}`)
    });
  }

  private logout(requestError: HttpErrorResponse, sessionExpired: boolean = false) {
    const error = {
      severity: "warning",
      summary: "You were logged out",
      message: "Reason is unknown",
    };

    const errorMessage = requestError.error.message;
    if (errorMessage === "Missing required cookies") {
      error.message = errorMessage;
    } else if (sessionExpired) {
      error.message = "Session is expired";
    } else {
      error.severity = "error";
    }

    this.authService.logout();
    this.router.navigate(["auth/login"]);
    return throwError(() => new HttpErrorResponse({error: error}));
  }
}
