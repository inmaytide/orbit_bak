import { BasicModel } from "./basic-model";

export class Permission extends BasicModel {
    code: string;
    name: string;
    action: string;
    icon: string;
    category: string;
    description: string;
    method: string;
    parent: string;
    sort: number;
    children: Permission[] = [];
}
