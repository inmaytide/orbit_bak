import { NgModule } from '@angular/core';
import {MainComponent} from './main.component';
import {HeaderComponent} from './header/header.component';

@NgModule({
    declarations: [
        MainComponent,
        HeaderComponent
    ],
    exports: [
        MainComponent
    ]
})
export class MainModule {

}
