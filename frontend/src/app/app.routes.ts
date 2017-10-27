import {LoginComponent} from './auth/login/login.component';
import {Error403Compontent} from "./errors/403.component";

export const appRoutes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', loadChildren: './home/home.module#HomeModule'},
  {path: 'error_403', component: Error403Compontent},
  {path: '**', component: LoginComponent} // fallback router must in the last
];
