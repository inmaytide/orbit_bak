import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MTreeComponent} from "./m-tree/m-tree";
import {FormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {MPaginationComponent} from "./m-pagination/m-pagination";
import {MTableComponent} from "./m-table/m-table";

@NgModule({
  declarations: [
    MTreeComponent,
    MPaginationComponent,
    MTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    TranslateModule,
    NgbPaginationModule
  ],
  exports: [
    MPaginationComponent,
    MTableComponent,
    MTreeComponent
  ]
})
export class MControlsModule {

}
