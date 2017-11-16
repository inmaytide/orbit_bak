import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {AppComponent} from './app.component';
import {LoginComponent} from './auth/login/login.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {LoginService} from "./auth/login/login.service";
import {FormsModule} from "@angular/forms";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HomeModule} from "./home/home.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LayerModule} from "./layers/layer.module";
import {Error403Compontent} from "./errors/403.component";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {CommonRequestInterceptor} from "./interceptors/common-request-interceptor";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import * as GlobalVariable from "./globals";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    Error403Compontent
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    HomeModule,
    RouterModule.forRoot(appRoutes),
    LayerModule,
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
    {provide: HTTP_INTERCEPTORS, useClass: CommonRequestInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, GlobalVariable.BASE_API_URL + "lang/", "");
}
