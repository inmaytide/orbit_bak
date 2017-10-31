import {PipeTransform} from "@angular/core";

export class MColumnModel {
  text: string;
  name: string;
  event: Function = undefined;
  pipe: PipeTransform = undefined;
  display: boolean = true;
}
