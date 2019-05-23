import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { AuthenticateFilter } from 'src/app/core/passport/authenticate.service';

const routes: Routes = [
    {
        path: '', component: MainComponent, canActivate: [AuthenticateFilter]
    },
    {
        path: 'login', component: LoginComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
