import {LoginComponent} from "./auz/login";
import {IndexComponent} from "./content/index/index";
import {MainComponent} from "./content/main";

export const appRoutes = [
  {path: '', loadChildren: './content/main.module#MainModule'},
  {path: 'login', component: LoginComponent},
  {path: '**', component: LoginComponent}
];
