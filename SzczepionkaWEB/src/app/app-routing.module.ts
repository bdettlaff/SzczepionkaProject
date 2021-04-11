import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './component/register/register.component';
import { MapComponent } from './component/map/map.component';
import { LandingPageComponent } from './component/landing-page/landing-page.component';
import { AppointmentDetailsComponent } from './component/appointment-details/appointment-details.component';
import {ConfirmationComponent} from "./component/confirmation/confirmation.component";

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'map', component: MapComponent},
  {path: 'appointmentDetails/:patientUUID', component: AppointmentDetailsComponent},
  {path: 'confirmation', component: ConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
