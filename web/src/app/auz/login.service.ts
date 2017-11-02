import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {GlobalVariables} from "../global-variables";
import {User} from "../models/user";
import {Token} from "./models/token";
import {CommonUtils} from "../common-utils";

@Injectable()
export class LoginService {

  private loginUrl = GlobalVariables.API_BASE_URL + "login";

  constructor(private http: HttpClient) {
  }

  public login(token: Token): Promise<void> {
    return this.http.post(this.loginUrl, token)
      .map(response => response as User)
      .toPromise()
      .then(user => CommonUtils.setPrincipal(user))
      .catch(reason => Promise.reject(reason));
  }

  public logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
  }

}
