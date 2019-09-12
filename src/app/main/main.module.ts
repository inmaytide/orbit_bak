import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from './main.component';
import {HeaderComponent} from './header/header.component';
import {NavComponent} from './sidebar/nav.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {RouterModule} from '@angular/router';
import {MainService} from './main.service';

@NgModule({
    declarations: [
        MainComponent,
        HeaderComponent,
        NavComponent,
        SidebarComponent
    ],
    imports: [
        NgZorroAntdModule,
        CommonModule,
        RouterModule
    ],
    exports: [
        MainComponent
    ],
    providers: [MainService]
})
export class MainModule {

}
