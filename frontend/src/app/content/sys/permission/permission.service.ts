import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Permission } from "../../../models/permission";
import { GlobalVariables } from "../../../global-variables";
import { PERMISSION_API_URL } from "./permission.config";

@Injectable()
export class PermissionService {


    constructor(private http: HttpClient) {

    }

    public list(): Promise<Permission[]> {
        return this.http.get(PERMISSION_API_URL.BASIC)
            .toPromise()
            .then(response => response as Permission[]);
    }

    public listMenus(): Promise<Permission[]> {
        return this.http.get(PERMISSION_API_URL.BASIC + "?category=377564822935437312")
            .toPromise()
            .then(response => response as Permission[])
    }

    public codeIsRepeat(code: string, id: string): { isRepeat: boolean } {
        const remote = PERMISSION_API_URL.BASIC + "/checkCode/" + id + "/" + code;
        const xhr = new XMLHttpRequest();
        let result = "{'isRepeat': true}";
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
        return JSON.parse(result);
    }


    public findUserMenus(username: string): Promise<Permission[]> {
        return this.http.get(PERMISSION_API_URL.LIST_USERS_MENUS + username)
            .toPromise()
            .then(data => data as Permission[], )
            .catch(reason => Promise.reject(reason));
    }

    public save(inst: Permission): Promise<Permission> {
        return this.http.post(PERMISSION_API_URL.BASIC, inst)
            .map(response => response as Permission)
            .toPromise();
    }

    public update(inst: Permission): Promise<Permission> {
        return this.http.put(PERMISSION_API_URL.BASIC, inst)
            .map(response => response as Permission)
            .toPromise();
    }

    public remove(id: string): Promise<any> {
        return this.http.delete(PERMISSION_API_URL.BASIC + "/" + id)
            .toPromise();
    }

}
