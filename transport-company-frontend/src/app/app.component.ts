import { Component } from '@angular/core';
import { PrimengOverridesService } from "./services/primeng-overrides.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'transport-company-frontend';

  constructor(private primengOverridesService: PrimengOverridesService) {
    this.primengOverridesService.init();
  }
}
