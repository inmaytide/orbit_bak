import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { GlobalVariables } from "../../../global-variables";
import { User } from "../../../models/user";
import { UserApiUrls } from "./user.api.urls";


@Injectable()
export class UserService {

    constructor(private http: HttpClient) {

    }

    public getUserByUsername(username: string): Promise<User> {
        return this.http.get(UserApiUrls.getUserByUsername + username)
            .toPromise()
            .then(response => response as User);
    }

}