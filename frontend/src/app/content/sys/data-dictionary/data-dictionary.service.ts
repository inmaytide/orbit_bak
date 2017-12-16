import { HttpClient, HttpParams } from "@angular/common/http";
import { GlobalVariables } from "../../../global-variables";
import { DataDictionary } from "../../../models/data-dictionary";
import { Injectable } from "@angular/core";
import { DATA_DICTIONARY_API_URL } from "./data-dictionary.config";
import { CommonUtils } from "../../../common-utils";

@Injectable()
export class DataDictionaryService {

    private _cache = {};

    constructor(private http: HttpClient) {

    }

    public getText(category: string, id: string): string {
        const dictionaries = this.listByCategory(category);
        let text = "";
        dictionaries.forEach(dictionary => {
            if (dictionary.id === id) {
                text = dictionary.text;
            }
        })
        return text;
    }

    public listByCategory(category: string): DataDictionary[] {
        if (!this._cache[category] || this._cache[category].length == 0) {
            this._cache[category] = this._listByCategory(category);
        }
        return this._cache[category];
    }

    private _listByCategory(category: string): DataDictionary[] {
        const remote = DATA_DICTIONARY_API_URL.BASIC + "?category=" + category;
        const xhr = new XMLHttpRequest();
        let result = new Array();
        xhr.open("get", remote, false);
        xhr.setRequestHeader("Authorization", CommonUtils.getToken());
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (xhr.responseText) {
                    result = JSON.parse(xhr.responseText)
                }
            }
        };
        xhr.send();
        return result;
    }
}
