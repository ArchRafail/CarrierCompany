import { HttpErrorResponse } from "@angular/common/http";


export function extractMessageFromError(httpErrorResponse: HttpErrorResponse) {
  return httpErrorResponse?.error?.message || httpErrorResponse?.message || httpErrorResponse?.statusText;
}
