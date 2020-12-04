import {NgModule} from '@angular/core';
import {Exception404Component} from './404.component';
import {ExceptionRoutingModule} from './exception-routing.module';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {Exception403Component} from './403.component';
import {TranslateModule} from '@ngx-translate/core';

@NgModule({
    declarations: [
        Exception404Component,
        Exception403Component
    ],
    imports: [ExceptionRoutingModule, NgZorroAntdModule, TranslateModule]
})
export class ExceptionModule {

}
