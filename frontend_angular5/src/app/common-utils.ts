import {GlobalVariables} from "./global-variables";
import {User} from "./models/user";

export class CommonUtils {

  public static getPrincipal(): any {
    let principal = localStorage.getItem(GlobalVariables.PRINCIPAL);
    return principal ? JSON.parse(principal) : {};
  }

  public static setPrincipal(principal) {
    localStorage.setItem(GlobalVariables.PRINCIPAL, JSON.stringify(principal));
  }

  public static getToken(): string {
    const principal = CommonUtils.getPrincipal();
    return principal && principal.token ? "Bearer " + principal.token : "-1"
  }

  public static handleErrors(reason) {
    console.log(reason);
  }

}
