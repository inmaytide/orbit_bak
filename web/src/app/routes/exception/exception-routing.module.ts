import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {Exception404Component} from './404.component';
import {Exception403Component} from './403.component';

const routes = [
    {path: '404', component: Exception404Component},
    {path: '403', component: Exception403Component}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ExceptionRoutingModule {
}
