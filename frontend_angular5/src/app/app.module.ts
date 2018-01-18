import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { RouterModule } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AppComponent } from './app.component';
import { LoginComponent } from "./auz/login";
import { NgZorroAntdModule, NZ_MESSAGE_CONFIG } from "ng-zorro-antd";
import { appRoutes } from "./app.routes";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoginService } from "./auz/login.service";
import { CommonRequestInterceptor } from "./interceptors/common-request-interceptor";
import { GlobalVariables } from "./global-variables";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MainModule } from "./content/main.module";
import { CommonModule } from "@angular/common";
import { DictionaryPipe } from "./pipe/dictionary.pipe";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        NgZorroAntdModule.forRoot(),
        RouterModule.forRoot(appRoutes),
        HttpClientModule,
        MainModule,
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
        { provide: HTTP_INTERCEPTORS, useClass: CommonRequestInterceptor, multi: true },
        { provide: NZ_MESSAGE_CONFIG, useValue: { nzDuration: 5000 } }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, GlobalVariables.API_BASE_URL + "lang/", "");
}
