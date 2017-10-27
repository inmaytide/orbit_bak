import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";
import {Attribute, Directive, forwardRef, Input} from "@angular/core";
import * as GlobalVariable from "../../globals";
import "rxjs/add/operator/toPromise";

@Directive({
  selector: '[notRepeat]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: forwardRef(() => NotRepeatValidator),
    multi: true
  }]
})
export class NotRepeatValidator implements Validator {

  @Input()
  public selfId: string;


  constructor(@Attribute("notRepeat") public notRepeat: string) {

  }

  validate(c: AbstractControl): { [key: string]: any } {
    let value = c.value;
    if (value === undefined || value === "" || value === null) {
      return;
    }

    let remote = GlobalVariable.BASE_API_URL + this.notRepeat + "/" + (this.selfId || "-1") + "/" + value;

    let xhr = new XMLHttpRequest();
    let bit = "true";
    xhr.withCredentials = true;
    xhr.open("get", remote, false);
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        if (xhr.responseText) {
          bit = xhr.responseText
        }
      }
    };
    xhr.send();

    if (bit == "false") {
      return {
        notRepeat: true
      };
    }
    return null;
  }

  registerOnValidatorChange(fn: () => void): void {

  }

}
