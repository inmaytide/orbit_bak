import {Injectable} from "@angular/core";
import * as GlobalVariable from "../../../globals";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Role} from "../../../models/role-model";
import {User} from "../../../models/user-model";

@Injectable()
export class RoleService {

  private url_base = GlobalVariable.BASE_API_URL + "sys/roles";
  private url_check_code = this.url_base + "/checkCode/";

  constructor(public http: HttpClient) {

  }

  public list(conditions: { keywords, number, size }): Observable<any> {
    return this.http.get(this.url_base, {params: new HttpParams({fromObject: conditions})});
  }

  public add(role: Role): Promise<Role> {
    return this.http.post(this.url_base, role)
      .toPromise()
      .then(response => response as Role)
      .catch(reason => Promise.reject(reason));
  }

  public checkCode(id: string, code: string): boolean {
    let remote = this.url_check_code + id + "/" + code;
    let xhr = new XMLHttpRequest();
    let bit = 'true';
    xhr.withCredentials = true;
    xhr.open("get", remote, false);
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        if (xhr.responseText) {
          bit = xhr.responseText;
        }
      }
    };
    xhr.send();
    return bit == 'true';
  }

  remove(ids: string[]) {
    return this.http.delete(this.url_base + "/" + ids.join(",")).toPromise();
  }

  associatePermissions(permssionIds: string, id: string): Promise<any> {
    let url = this.url_base + "/" + id + "/permissions/" + permssionIds;
    return this.http.patch(url, {}).toPromise()
  }

  get(id: string) : Promise<Role> {
    let url = this.url_base + "/" + id;
    return this.http.get(url)
      .toPromise()
      .then(response => response as Role)
      .catch(reason => Promise.reject(reason));
  }

  associateUsers(users: User[], id: string): Promise<any> {
    let userIds = [];
    users.forEach(user => userIds.push(user.id));
    let url = this.url_base + "/" + id + "/users/" + userIds.join(",");
    return this.http.patch(url, {}).toPromise();

  }
}
