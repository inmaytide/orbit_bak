import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Permission} from "../../../models/permission";
import {GlobalVariables} from "../../../global-variables";

@Injectable()
export class PermissionService {

  private getUserMenusApi = GlobalVariables.API_BASE_URL + "user/menus";
  private permissionApi = GlobalVariables.API_BASE_URL + "sys/permissions";

  constructor(private http: HttpClient) {

  }

  public list(): Promise<Permission[]> {
    return this.http.get(this.permissionApi)
      .map(response => response as Permission[])
      .toPromise();
  }

  public listMenus(): Promise<Permission[]> {
    return this.http.get(this.permissionApi + "?category=MENU")
      .map(response => response as Permission[])
      .toPromise();
  }

  public codeIsRepeat(code: string, id: string): string {
    const remote = this.permissionApi + "/checkCode/" + id + "/" + code;
    const xhr = new XMLHttpRequest();
    let result = "true";
    xhr.withCredentials = true;
    xhr.open("get", remote, false);
    xhr.onreadystatechange = () => {
      if (xhr.readyState === 4 && xhr.status === 200) {
        if (xhr.responseText) {
          result = xhr.responseText;
        }
      }
    };
    xhr.send();
    return result;
  }


  public findUserMenus(): Promise<Permission[]> {
    return this.http.get(this.getUserMenusApi)
      .toPromise()
      .then(data => data as Permission[])
      .catch(reason => Promise.reject(reason));
  }

}
