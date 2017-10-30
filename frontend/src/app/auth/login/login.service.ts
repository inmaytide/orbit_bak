import {Injectable} from '@angular/core';
import {User} from '../../models/user-model';
import {Token} from "../../models/token-model";
import {HttpClient} from "@angular/common/http";
import * as GlobalVariable from "../../globals";

@Injectable()
export class LoginService {

  public loginURL = GlobalVariable.BASE_API_URL + 'login';

  constructor(public http: HttpClient) {
  }

  public login(token: Token): Promise<User> {
    return this.http
      .post(this.loginURL, token)
      .toPromise()
      .then(user => {
        localStorage.setItem(GlobalVariable.CURRENT_USER, JSON.stringify(user));
        return user as User;
      }).catch(reason => Promise.reject(reason));
  }

  public logout(): void {
    localStorage.removeItem(GlobalVariable.CURRENT_USER);
  }

}
