import {NgModule} from "@angular/core";
import {MTreeComponent} from "./m-tree";
import {CommonModule} from "@angular/common";
import {MTreeNode} from "./m-tree-node";

@NgModule({
  declarations: [
    MTreeComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    MTreeComponent
  ]
})
export class MTreeModule {

}
