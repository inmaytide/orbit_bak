import {NgModule} from "@angular/core";
import {LayerAlert} from "./layer.alert";
import {LayerConfirm} from "./layer.confirm";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [
    LayerAlert,
    LayerConfirm
  ],
  entryComponents: [
    LayerAlert,
    LayerConfirm
  ],
  imports: [
    TranslateModule
  ]
})

export class LayerModule {

}
