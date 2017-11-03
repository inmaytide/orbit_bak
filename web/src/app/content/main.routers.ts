import {MainComponent} from "./main";
import {IndexComponent} from "./index/index";

export const mainRouters = [{
  path: '',
  component: MainComponent,
  children: [
    {path: '', component: IndexComponent},
    {path: 'index', component: IndexComponent}
  ]
}];
