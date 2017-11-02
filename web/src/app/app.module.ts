import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {NgModule} from '@angular/core';
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppComponent} from './app.component';
import {LoginComponent} from "./auz/login";
import {NgZorroAntdModule, NZ_MESSAGE_CONFIG} from "ng-zorro-antd";
import {appRoutes} from "./app.routes";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {LoginService} from "./auz/login.service";
import {CommonRequestInterceptor} from "./interceptors/common-request-interceptor";
import {GlobalVariables} from "./global-variables";
import {IndexComponent} from "./content/index/index";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgZorroAntdModule.forRoot(),
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    LoginService,
    HttpClient,
    {provide: HTTP_INTERCEPTORS, useClass: CommonRequestInterceptor, multi: true},
    {provide: NZ_MESSAGE_CONFIG, useValue: {nzDuration: 5000}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, GlobalVariables.API_BASE_URL + "lang/", "");
}
