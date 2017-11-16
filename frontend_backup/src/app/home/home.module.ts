import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router"
import {appRoutes} from "./home.routes";
import {ContentComponent} from "./content.component";
import {HomeComponent} from "./home.component";
import {SidebarComponent} from "./menu/sidebar.component";
import {TopnavbarComponent} from "./menu/topnavbar.component";
import {FooterComponent} from "./footer.component";
import {SysModule} from "./sys/sys.module";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";


@NgModule({
  declarations: [
    HomeComponent,
    SidebarComponent,
    TopnavbarComponent,
    FooterComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(appRoutes),
    SysModule,
    NgbDropdownModule
  ]
})

export class HomeModule {

}
