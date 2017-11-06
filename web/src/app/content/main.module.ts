import {NgModule} from "@angular/core";
import {PermissionService} from "./sys/permission/permission.service";
import {IndexComponent} from "./index/index";
import {SidebarComponent} from "./nav/sidebar.component";
import {RouterModule} from "@angular/router";
import {mainRouters} from "./main.routers";
import {MainComponent} from "./main";
import {NgZorroAntdModule} from "ng-zorro-antd";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {MainTopComponent} from "./frames/main-top";
import {PermissionComponent} from "./sys/permission/permission";
import {MainFooterComponent} from "./frames/main-footer";
import {PermissionInfoComponent} from "./sys/permission/permission.info";

@NgModule({
  declarations: [
    MainComponent,
    IndexComponent,
    SidebarComponent,
    MainTopComponent,
    PermissionComponent,
    PermissionInfoComponent,
    MainFooterComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgZorroAntdModule,
    RouterModule.forChild(mainRouters)
  ],
  providers: [
    PermissionService
  ],
  entryComponents: [
    PermissionInfoComponent
  ]
})
export class MainModule {

}
