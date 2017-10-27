import {BasicModel} from "./basic-model";
import {Permission} from "./permission-model";

export class Role extends BasicModel {
  code: string;
  name: string;
  description: string;
  permissions: Permission[] = [];
}
