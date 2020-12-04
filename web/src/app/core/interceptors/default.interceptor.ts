import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponseBase, HttpErrorResponse} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {mergeMap, catchError} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {Injectable, Injector} from '@angular/core';
import {NzMessageService} from 'ng-zorro-antd';
import {AuthenticateService} from '../passport/authenticate.service';
import {TranslateService} from '@ngx-translate/core';
import {Router} from '@angular/router';

@Injectable()
export class DefaultInterceptor implements HttpInterceptor {

    constructor(private injector: Injector,
                private translate: TranslateService) {
    }

    private get message(): NzMessageService {
        return this.injector.get(NzMessageService);
    }

    private get authenticate(): AuthenticateService {
        return this.injector.get(AuthenticateService);
    }

    private get router(): Router {
        return this.injector.get(Router);
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // 重写URL，统一加上后台服务端content path
        let url = request.url;
        if (!url.startsWith('http://') && !url.startsWith('https://') && url.indexOf('/assets/lang')) {
            url = environment.contextPath + url;
        }
        const options: any = {
            url
        };

        const accessToken = this.authenticate.getAccessToken();
        if (accessToken !== '') {
            options.setHeaders = {Authorization: `Bearer ${accessToken}`};
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


    private showMessage(ev: HttpResponseBase): Observable<any> {
        const err = (ev as HttpErrorResponse).error;
        console.log(ev);
        let processed = false;
        if (ev.status === 403) {
            processed = true;
            if (ev.url.endsWith('/oauth/token')) {
                if (err.error_description === 'err_bad_credentials') {
                    this.translate.get('error.bad_credentials').subscribe(res => this.message.error(res));
                } else if (err.error_description === 'err_login_refused') {
                    this.translate.get('error.login_refused').subscribe(res => this.message.error(res));
                }
            } else {
                this.router.navigateByUrl('/exception/403');
            }
        }

        if (ev.status === 500) {
            processed = true;
            this.message.error(err.message);
        }

        if (ev.status === 404) {
            processed = true;
            this.router.navigateByUrl('/exception/404');
        }

        if (ev.status === 401) {
            processed = true;
            this.authenticate.clear();
            this.router.navigateByUrl('/login');
            this.translate.get('error.unauthorized').subscribe(res => this.message.warning(res));

        }

        if (!processed) {
            const key = `error.status.${ev.status}`;
            this.translate.get(key)
                .subscribe(res => this.message.error(res === key ? ev.statusText : res));
        }
        return throwError(err);
    }


    private handleData(ev: HttpResponseBase): Observable<any> {
        if (ev.status >= 200 && ev.status < 300) {
            return of(ev);
        }
        return this.showMessage(ev);
    }

}
