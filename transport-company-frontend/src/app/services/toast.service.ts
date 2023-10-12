import { Injectable } from '@angular/core';
import { MessageService } from "primeng/api";
import { HttpErrorResponse } from "@angular/common/http";
import { extractMessageFromError } from "../helpers/CommonHelper";


@Injectable({
  providedIn: 'root'
})
export class ToastService {

  public handleHttpError = (error: HttpErrorResponse) => {
    const errorBody = error.error;
    if (errorBody?.severity === "warning") {
      this.warning(errorBody.summary || "Http warning", errorBody.message)
    } else {
      this.error(
        errorBody?.summary || "Http error",
        extractMessageFromError(error)
      );
      console.error(error);
    }
  }

  constructor(private messageService: MessageService) { }

  show(severity: "success" | "info" | "warn" | "error", summary: string, details: string | undefined, lifetime: number) {
    this.messageService.add({severity, summary, detail: details, life: lifetime});
  }

  info(summary: string, details?: string) {
    this.show('info', summary, details, 3000);
  }

  success(summary: string, details?: string) {
    this.show('success', summary, details, 3000);
  }

  error(summary: string, details?: string) {
    this.show('error', summary, details, 10000);
  }

  warning(summary: string, details?: string) {
    this.show('warn', summary, details, 3000);
  }

  methodNotImplemented() {
    this.error("Method not implemented", "This method is not implemented yet");
  }
}
