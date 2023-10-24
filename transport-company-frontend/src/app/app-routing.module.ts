import { RouterModule, Routes } from "@angular/router";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { NgModule } from "@angular/core";
import { PageNotFoundComponent } from "./components/page-not-found/page-not-found.component";


const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'warehouses', loadChildren: () => import('./components/warehouses/warehouses.module').then(m => m.WarehousesModule)},
  {path: 'transporters', loadChildren: () => import('./components/transporters/transporters.module').then(m => m.TransportersModule)},
  {path: 'deliveries', loadChildren: () => import('./components/deliveries/deliveries.module').then(m => m.DeliveriesModule)},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
