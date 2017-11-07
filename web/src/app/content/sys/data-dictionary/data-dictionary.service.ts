import {HttpClient} from "@angular/common/http";
import {GlobalVariables} from "../../../global-variables";
import {DataDictionary} from "../../../models/data-dictionary";
import {Injectable} from "@angular/core";

@Injectable()
export class DataDictionaryService {

  private listByCategoryApi = GlobalVariables.API_BASE_URL + "sys/data-dictionaries?category=";

  constructor(private http: HttpClient) {

  }

  public listByCategory(category: string): Promise<DataDictionary[]> {
    return this.http.get(this.listByCategoryApi + category)
      .map(response => response as DataDictionary[])
      .toPromise();
  }
}
