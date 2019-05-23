import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgZorroAntdModule, NZ_I18N, zh_CN } from 'ng-zorro-antd';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { AuthenticateService, AuthenticateFilter } from './core/passport/authenticate.service';
import { DefaultInterceptor } from './core/interceptors/default.interceptor';

registerLocaleData(zh);

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        MainComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgZorroAntdModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule
    ],
    providers: [
        AuthenticateFilter,
        AuthenticateService,
        FormBuilder,
        { provide: NZ_I18N, useValue: zh_CN },
        { provide: HTTP_INTERCEPTORS, useClass: DefaultInterceptor, multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
