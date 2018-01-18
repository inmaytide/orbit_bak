import { BasicModel } from "./basic-model";


export class Organization extends BasicModel {
    code: string;
    name: string;
    category: string;
    parent: string;
    address: string;
    description: string;
    sort: number;
}