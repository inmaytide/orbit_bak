import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {User} from "../../../models/user-model";
import * as GlobalVariable from "../../../globals";
import {MPage} from "../../../m-controls/models/m-page-model";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable()
export class UserService {

  private baseUrl: string = GlobalVariable.BASE_API_URL + "sys/users";

  constructor(private http: HttpClient) {

  }

  public list(conditions: {}): Observable<MPage<User>> {
    return this.http.get(this.baseUrl, {params: new HttpParams({fromObject:conditions})})
      .map(response => response as MPage<User>);
  }

  public remove(ids: string[]): Promise<any> {
    return this.http.delete(this.baseUrl + "/" + ids.join(",")).toPromise();
  }
}
