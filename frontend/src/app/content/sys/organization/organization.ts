import { OnInit, Component } from "@angular/core";
import { OrganizationService } from "./organization.service";

@Component({
    selector: "organization",
    templateUrl: "./organization.html"
})
export class OrganizationComponent implements OnInit {

    constructor(private service: OrganizationService) {
    }

    ngOnInit() {

    }

}