import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { PageNotFoundComponent } from "./components/page-not-found/page-not-found.component";
import { PageUnderConstructionComponent } from "./components/page-under-construction/page-under-construction.component";
import { notAuthenticatedGuard } from "./guards/not-authenticated-guard";
import { authenticatedGuard } from "./guards/authenticated-guard";


const routes: Routes = [
  {path: 'auth', loadChildren: () => import('./components/auth/auth.module').then(m => m.AuthModule), canMatch: [notAuthenticatedGuard]},
  {path: 'warehouses', loadChildren: () => import('./components/warehouses/warehouses.module').then(m => m.WarehousesModule), canMatch: [authenticatedGuard]},
  {path: 'transporters', loadChildren: () => import('./components/transporters/transporters.module').then(m => m.TransportersModule), canMatch: [authenticatedGuard]},
  {path: 'deliveries', loadChildren: () => import('./components/deliveries/deliveries.module').then(m => m.DeliveriesModule), canMatch: [authenticatedGuard]},
  {path: 'settings', loadChildren: () => import('./components/settings/settings.module').then(m => m.SettingsModule), canMatch: [authenticatedGuard]},
  {path: 'page-construction', component: PageUnderConstructionComponent, canMatch: [authenticatedGuard]},
  {path: '', loadChildren: () => import('./components/dashboard/dashboard.module').then(m => m.DashboardModule), canMatch: [authenticatedGuard]},
  {path: '**', component: PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
