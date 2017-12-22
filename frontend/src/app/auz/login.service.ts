import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { GlobalVariables } from "../global-variables";
import { User } from "../models/user";
import { Token } from "./models/token";
import { CommonUtils } from "../common-utils";
import { UserService } from "../content/sys/user/user.service";

@Injectable()
export class LoginService {

  private loginUrl = GlobalVariables.API_BASE_URL + "oauth/token";

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
    return this.http.post(this.loginUrl, {}, { params: params })
      .toPromise()
      .then(response => {
        const user = { username: token.username, token: response["access_token"] };
        CommonUtils.setPrincipal(user);
        this.loadUserDetails(user);
      })
      .catch(reason => Promise.reject(reason));
  }

  public logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
  }

}
