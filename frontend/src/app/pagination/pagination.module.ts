import {NgModule} from "@angular/core";
import {PaginationComponent} from "./pagination.component";
import {TranslateModule} from "@ngx-translate/core";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    PaginationComponent
  ],
  imports: [
    FormsModule,
    TranslateModule,
    NgbPaginationModule
  ],
  exports: [
    PaginationComponent
  ]
})
export class PaginationModule {

}
