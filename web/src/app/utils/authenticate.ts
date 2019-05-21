export class AuthenticateUtils {

  static KEY_USER = "__ORBIT_USER__";

  static isAuthenticated() {
    const value = sessionStorage.getItem(AuthenticateUtils.KEY_USER);
    return value !== null;
  }

}

export class RequestToken {
  grant_type: string = "password";
  client_id: string = "orbit";
  scope: string = "all";
  client_secret: string = "59a84cbf83227a35";
  username: string;
  password: string;
  refresh_token: string;
  captche: string;
  captche_name: string;
}
