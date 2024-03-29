import { Component, OnInit } from '@angular/core';
import { MenuItem } from "primeng/api";
import { map, Observable } from "rxjs";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";


@Component({
  selector: 'app-layout-header',
  templateUrl: './layout-header.component.html',
  styleUrls: ['./layout-header.component.scss']
})
export class LayoutHeaderComponent implements OnInit{
  items: MenuItem[] | undefined;
  activeItem: MenuItem | undefined;
  accountMenuItems$!: Observable<MenuItem[]>;

  constructor(protected authService: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.items = [
      { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: "/", routerLinkActiveOptions: { exact: true } },
      { label: 'Warehouses', icon: 'pi pi-fw pi-building', routerLink: "warehouses", routerLinkActiveOptions: { exact: true }  },
      { label: 'Transporters', icon: 'pi pi-fw pi-truck', routerLink: "transporters", routerLinkActiveOptions: { exact: true } },
      { label: 'Deliveries', icon: 'pi pi-fw pi-images', routerLink: "deliveries", routerLinkActiveOptions: { exact: true } },
      { label: 'Settings', icon: 'pi pi-fw pi-cog', routerLink: "settings", routerLinkActiveOptions: { exact: true }  }
    ];
    this.activeItem = this.items[0];
    this.initProfileMenuObservable();
  }

  initProfileMenuObservable() {
    this.accountMenuItems$ = this.authService.user$
      .pipe(
        map(user =>
        [
          {
            label: user?.email,
            items: [
              {
                label: 'Dummy',
                visible: false
              },
              {
                label: 'Logout',
                icon: 'pi pi-fw pi-sign-out',
                command: () => {
                  this.authService.logout();
                  this.router.navigate(["/auth/login"]);
                }
              }
            ]
          }
        ])
      )
  }
}
