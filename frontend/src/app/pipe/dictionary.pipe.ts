import { PipeTransform } from "@angular/core";
import { Pipe } from "@angular/core";
import { DataDictionaryService } from "../content/sys/data-dictionary/data-dictionary.service";


@Pipe({name: "dictionary"})
export class DictionaryPipe implements PipeTransform {

    constructor(private service: DataDictionaryService) {

    }

    transform(value: any, category: string) {
        return this.service.getText(category, value);
    }
    
}