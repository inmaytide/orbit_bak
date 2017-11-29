import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalVariables} from "../../../global-variables";
import {DataDictionary} from "../../../models/data-dictionary";
import {Injectable} from "@angular/core";
import { DataDictionaryApiConfig } from "./data-dictionary.api.config";
import { CommonUtils } from "../../../common-utils";

@Injectable()
export class DataDictionaryService {

  public _cache = {};

  constructor(private http: HttpClient) {

  }

  public listByCategory(category: string) {
    return this._cache[category] ? Promise.resolve(this._cache[category]) : this._listByCategory(category);
  }

  private _listByCategory(category: string) {
    const params = new HttpParams({fromObject: {"category": category}});
    return this.http.get(DataDictionaryApiConfig.basic, {params: params})
      .map(response => response as DataDictionary[])
      .toPromise()
      .then(data => this._cache[category] = data)
      .catch(reason => CommonUtils.handleErrors(reason));
  }
}
