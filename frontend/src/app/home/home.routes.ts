import {ContentComponent} from "./content.component";
import {HomeComponent} from "./home.component";
import {PermissionComponent} from "./sys/permission/permission.component";
import {LogComponent} from "./sys/log/log.component";
import {RoleComponent} from "./sys/role/role.component";
import {UserComponent} from "./sys/user/user.component";

export const appRoutes = [{
  path: 'home',
  component: HomeComponent,
  children: [
    {path: '', component: ContentComponent},
    {path: 'content', component: ContentComponent},
    {path: 'permission', component: PermissionComponent},
    {path: 'log', component: LogComponent},
    {path: 'role', component: RoleComponent},
    {path: 'user', component: UserComponent}
  ]
}];
