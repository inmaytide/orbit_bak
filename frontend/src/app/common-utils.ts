import {GlobalVariables} from "./global-variables";
import {User} from "./models/user";

export class CommonUtils {

  public static getPrincipal(): any {
    let principal = localStorage.getItem(GlobalVariables.PRINCIPAL);
    return principal ? JSON.parse(principal) : {};
  }

  public static setPrincipal(token) {
    localStorage.setItem(GlobalVariables.PRINCIPAL, JSON.stringify(token));
  }

  public static handleErrors(reason) {
    console.log(reason);
  }

}
