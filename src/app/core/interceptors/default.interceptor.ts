import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponseBase, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { mergeMap, catchError } from 'rxjs/operators'
import { environment } from 'src/environments/environment';
import { Injectable, Injector } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd';
import { AuthenticateService } from '../passport/authenticate.service';

const ERROR_MESSAGES = {
    503: "后台服务不可用<br/>服务器过载或维护中...",
    504: "网关连接超时",
    err_bad_credentials: "用户名或密码输入错误",
    err_login_restricted: (limit: string) => limit === "-1" ? "用户名或密码输入错误次数过多, 访问被拒绝" : `用户名或密码错误次数过多，分钟后再试`
}

@Injectable()
export class DefaultInterceptor implements HttpInterceptor {

    constructor(private injector: Injector) { }

    private get notification(): NzNotificationService {
        return this.injector.get(NzNotificationService);
    }

    private get authenticate(): AuthenticateService {
        return this.injector.get(AuthenticateService);
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // 重写URL，统一加上后台服务端content path
        let url = request.url;
        if (!url.startsWith("http://") && !url.startsWith("https://") && url.indexOf("/assets/lang")) {
            url = environment.contextPath + url;
        }
        const options: any = {
            url: url
        }

        const accessToken = this.authenticate.getAccessToken();
        if (accessToken !== "") {
            options.setHeaders = {"Authorization": `Bearer ${accessToken}`}; 
        }
        
        return next.handle(request.clone(options)).pipe(
            mergeMap((event: any) => {
                // event 为 HttpResponse 时表示请求完成
                if (event instanceof HttpResponseBase) {
                    return this.handleData(event);
                }
                return of(event);
            }),
            catchError((err: HttpErrorResponse) => this.handleData(err)),
        );
    }


    private showNotification(ev: HttpResponseBase): Observable<any> {
        const err = (ev as HttpErrorResponse).error;
        let filled = null;
        if (ev.status === 403) {
            if (err.error_description === "err_bad_credentials") {
                filled = this.notification.error(ERROR_MESSAGES['err_bad_credentials'], ``);
            } else if (err.error_description.startsWith("err_login_restricted")) {
                const limit = err.error_description.replace("err_login_restricted_", "");
                filled = this.notification.error(ERROR_MESSAGES.err_login_restricted(limit), ``);
            } else if (err.error_description === "err_bad_captcha") {
                filled = "PROCESSED";
            }
        }

        if (ev.status === 500) {
            filled = this.notification.error(err.message, ``);
        }

        if (!filled) {
            const message = ERROR_MESSAGES[ev.status] || ev.statusText;
            this.notification.error(message, ``);
        }
        return throwError(err);
    }


    private handleData(ev: HttpResponseBase): Observable<any> {
        if (ev.status >= 200 && ev.status < 300) {
            console.log(ev);
            return of(ev);
        }
        return this.showNotification(ev);
    }

}
