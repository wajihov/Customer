import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerComponent } from './customer/customer.component';
import { ContactComponent } from './contact/contact.component';
import { DashbordComponent } from './dashbord/dashbord.component';

const routes: Routes = [
  { path: "", component: DashbordComponent },
  { path: "customer", component: CustomerComponent },
  { path: "contact", component: ContactComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
