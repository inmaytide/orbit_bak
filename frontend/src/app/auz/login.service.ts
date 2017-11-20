import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {GlobalVariables} from "../global-variables";
import {User} from "../models/user";
import {Token} from "./models/token";
import {CommonUtils} from "../common-utils";

@Injectable()
export class LoginService {

  private loginUrl = GlobalVariables.API_BASE_URL + "oauth/token";

  constructor(private http: HttpClient) {
  }

  public login(token: Token): Promise<void> {
  
    const params = new HttpParams({fromObject: Object.assign(token)});
    return this.http.post(this.loginUrl, {}, {params: params})
      .toPromise()
      .then(user => CommonUtils.setPrincipal(user))
      .catch(reason => Promise.reject(reason));
  }

  public logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
  }

}
