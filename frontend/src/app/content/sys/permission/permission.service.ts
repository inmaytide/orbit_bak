import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Permission } from "../../../models/permission";
import { GlobalVariables } from "../../../global-variables";
import { Observable } from "rxjs/Observable";
import { PermissionApiUrls } from "./permission.api.urls";

@Injectable()
export class PermissionService {


  constructor(private http: HttpClient) {

  }

  public list(): Promise<Permission[]> {
    return this.http.get(PermissionApiUrls.permissions)
      .map(response => response as Permission[])
      .toPromise();
  }

  public listMenus(): Promise<Permission[]> {
    return this.http.get(PermissionApiUrls.permissions + "?category=MENU")
      .map(response => response as Permission[])
      .toPromise();
  }

  public codeIsRepeat(code: string, id: string): string {
    const remote = PermissionApiUrls.permissions + "/checkCode/" + id + "/" + code;
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


  public findUserMenus(username: string): Promise<Permission[]> {
    return this.http.get(PermissionApiUrls.listUserMenus + username)
      .toPromise()
      .then(data => data as Permission[], )
      .catch(reason => Promise.reject(reason));
  }

  public save(inst: Permission): Promise<Permission> {
    return this.http.post(PermissionApiUrls.permissions, inst)
      .map(response => response as Permission)
      .toPromise();
  }

}
