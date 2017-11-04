import {NgModule} from "@angular/core";
import {PermissionService} from "./sys/permission/permission.service";
import {IndexComponent} from "./index/index";
import {SidebarComponent} from "./nav/sidebar.component";
import {RouterModule} from "@angular/router";
import {mainRouters} from "./main.routers";
import {MainComponent} from "./main";
import {NgZorroAntdModule} from "ng-zorro-antd";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CommonModule} from "@angular/common";
import {MainTopComponent} from "./top/main-top";

@NgModule({
  declarations: [
    MainComponent,
    IndexComponent,
    SidebarComponent,
    MainTopComponent
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
  ]
})
export class MainModule {

}
