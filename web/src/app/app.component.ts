import {Component} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {GlobalVariables} from "./global-variables";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(translate: TranslateService) {
    translate.setDefaultLang(GlobalVariables.DEFAULT_LANG);
  }
}
