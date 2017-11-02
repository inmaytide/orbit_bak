import {LoginComponent} from "./auz/login";
import {IndexComponent} from "./content/index/index";

export const appRoutes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'index', component: IndexComponent}
];
