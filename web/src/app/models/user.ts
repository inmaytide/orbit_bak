import {BasicModel} from "./basic-model";

export class User extends BasicModel {
  username: string;
  name: string;
  password: string;
  status: number;
  email: string;
  qq: string;
  wechat: string;
  telephone: string;
  cellphone: string;
  photo: string;
  superAdmin: number;
  remark: string;
  token: string;
}
