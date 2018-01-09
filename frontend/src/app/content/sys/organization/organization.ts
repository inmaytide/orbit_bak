import { OnInit, Component } from "@angular/core";
import { OrganizationService } from "./organization.service";
import { Organization } from "../../../models/organization";
import { NzModalService } from "ng-zorro-antd";
import { TranslateService } from "@ngx-translate/core";
import { OrganizationInfoComponent } from "./organization.info";

@Component({
    selector: "organization",
    templateUrl: "./organization.html"
})
export class OrganizationComponent implements OnInit {

    private data: Organization[] = [];

    constructor(private service: OrganizationService,
        private modalService: NzModalService,
        private translate: TranslateService) {
    }

    ngOnInit() {

    }

    public getData(path: string[]) {

    }

    public add() {
        const subscription = this.modalService.open({
            title: this.translate.getParsedResult({}, "organization.modal.title"),
            content: OrganizationInfoComponent,
            componentParams: { state: 'add' },
            footer: false,
            maskClosable: false,
            showConfirmLoading: true,
            width: 670,
            onOk() {

            }
        });

        subscription.subscribe(result => {
            if (typeof (result) == "object") {
                this.getData(result as string[]);
            }
        })
    }



}