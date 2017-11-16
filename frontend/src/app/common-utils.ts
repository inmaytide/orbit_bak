import {GlobalVariables} from "./global-variables";
import {User} from "./models/user";

export class CommonUtils {

  public static getPrincipal(): User {
    let principal = localStorage.getItem(GlobalVariables.PRINCIPAL);
    return principal ? JSON.parse(principal) : new User();
  }

  public static setPrincipal(user: User) {
    localStorage.setItem(GlobalVariables.PRINCIPAL, JSON.stringify(user));
  }

  public static handleErrors(reason) {
    console.log(reason);
  }

}
