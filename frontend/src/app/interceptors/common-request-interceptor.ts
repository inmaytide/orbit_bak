import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {CommonUtils} from "../common-utils";

@Injectable()
export class CommonRequestInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let headers = new HttpHeaders();
    let token = CommonUtils.getPrincipal();
    if (token && token.access_token) {
      headers = new HttpHeaders({
        "Authorization": token.access_token
      })
    }
    req = req.clone({
      withCredentials: true,
      headers: headers
    });
    return next.handle(req);
  }

}
