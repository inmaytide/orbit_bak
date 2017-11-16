import {PipeTransform} from "@angular/core";

export class MColumnModel {
  text: string;
  name: string;
  event?: Function;
  pipe?: PipeTransform  = undefined;
  display?: boolean = true;
}
