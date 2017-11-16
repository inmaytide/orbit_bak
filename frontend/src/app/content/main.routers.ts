import {MainComponent} from "./main";
import {IndexComponent} from "./index/index";
import {PermissionComponent} from "./sys/permission/permission";

export const mainRouters = [{
  path: '',
  component: MainComponent,
  children: [
    {path: '', component: IndexComponent},
    {path: 'index', component: IndexComponent},
    {path: 'permission', component: PermissionComponent}
  ]
}];
