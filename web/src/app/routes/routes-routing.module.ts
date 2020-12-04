import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from 'src/app/login/login.component';
import {MainComponent} from 'src/app/main/main.component';
import {AuthenticateFilter} from 'src/app/core/passport/authenticate.service';

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        canActivate: [AuthenticateFilter],
        children: [
            {path: 'exception', loadChildren: () => import('./exception/exception.module').then(m => m.ExceptionModule)}
        ]
    },
    {
        path: 'login', component: LoginComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class RoutesRoutingModule {
}
