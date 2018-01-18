import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Injectable } from "@angular/core";
import { CommonUtils } from "../common-utils";

@Injectable()
export class CommonRequestInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let principal = CommonUtils.getPrincipal();
        let headers = req.headers;
        if (principal && principal.token) {
            const clone = req.clone({setHeaders: {"Authorization": "Bearer " + principal.token}})
            return next.handle(clone);
        }
        return next.handle(req);
    }

}
