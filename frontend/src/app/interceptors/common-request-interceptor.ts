import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Injectable } from "@angular/core";
import { CommonUtils } from "../common-utils";

@Injectable()
export class CommonRequestInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let principal = CommonUtils.getPrincipal();
        if (principal && principal.token) {
            req.headers.set("Authorization", "Bearer " + principal.token);
        }
        req = req.clone({
            withCredentials: true
        });
        return next.handle(req);
    }

}
