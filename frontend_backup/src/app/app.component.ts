import {Component} from '@angular/core';
import 'rxjs/add/operator/merge';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  private globalClickCallbackFn: Function;
  private loginSuccessCallbackFn: Function;

  constructor(translate: TranslateService) {
    translate.setDefaultLang("en");
  }

  ngOnInit() {

  }

  ngOnDestroy() {
    if (this.globalClickCallbackFn) {
      this.globalClickCallbackFn();
    }
  }
}
