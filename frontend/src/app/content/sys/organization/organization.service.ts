import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ORGANIZATION_API_URL } from "./organization.config";
import { Organization } from "../../../models/organization";


@Injectable()
export class OrganizationService {

    constructor(private http: HttpClient) {
    }

    public list(parent: string): Promise<Organization[]> {
        return this.http.get(ORGANIZATION_API_URL.BASIC + "?parent=" + parent)
        .toPromise()
        .then(response => response as Organization[]);
    }

}