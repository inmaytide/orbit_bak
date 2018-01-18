import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { GlobalVariables } from "../global-variables";
import { User } from "../models/user";
import { Token } from "./models/token";
import { CommonUtils } from "../common-utils";
import { Observable } from "rxjs/Rx";
import { UserService } from "../content/sys/user/user.service";

@Injectable()
export class LoginService {

  private loginUrl = GlobalVariables.API_BASE_URL + "oauth/token";
  private currentCaptchaName;

  constructor(private http: HttpClient,
    private service: UserService) {
  }

  private loadUserDetails(user: { username, token }) {
    this.service.getUserByUsername(user.username)
      .then(response => CommonUtils.setPrincipal(Object.assign(response, user)))
      .catch(reason => Promise.reject(reason));
  }

  public login(token: Token): Promise<void> {
    const params = new HttpParams({ fromObject: Object.assign(token) });
    const headers = new HttpHeaders({"Captcha-Name": this.currentCaptchaName})
    return this.http.post(this.loginUrl, {}, { params: params, headers: headers })
      .toPromise()
      .then(response => {
        const user = { username: token.username, token: response["access_token"] };
        CommonUtils.setPrincipal(user);
        this.loadUserDetails(user);
      })
      .catch(reason => Promise.reject(reason));
  }

  public getCaptcha(): Promise<string> {
    return this.http.get(GlobalVariables.API_BASE_URL + "captcha?v=" + Date.now())
    .toPromise()
    .then(res => {
      this.currentCaptchaName = res['captchaName'];
      return res['image'];
    });
  }

  public logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
  }

}
