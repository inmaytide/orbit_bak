import { Component, OnInit, Input } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Permission } from "../../../models/permission";
import { NzModalSubject } from "ng-zorro-antd";
import { CommonUtils } from "../../../common-utils";
import { DataDictionary } from "../../../models/data-dictionary";
import { DataDictionaryService } from "../data-dictionary/data-dictionary.service";
import { isUndefined } from "util";
import { Organization } from "../../../models/organization";
import { OrganizationService } from "./organization.service";

@Component({
    selector: "organization-info",
    templateUrl: './organization.info.html',
    styles: [
        `
      :host ::ng-deep .customize-footer {
        border-top: 1px solid #e9e9e9;
        padding: 10px 18px 0 10px;
        text-align: right;
        border-radius: 0 0 0px 0px;
        margin: 15px -16px -5px -16px;
      }
    `,
        `
      :host ::ng-deep .customize-footer button {
        padding-left: 20px;
        padding-right: 20px;
        margin-right: 15px;
      }
    `,
        `
      .ant-form-item {
        margin-bottom: 12px;
      }
    `,
        `
      :host ::ng-deep .inline-error-text {
        color: red;
        line-height: 32px;
        padding-left: 7px;
        text-overflow: ellipsis;
        overflow-x: hidden;
        white-space: nowrap;
      }
    `
    ]
})
export class OrganizationInfoComponent implements OnInit {

    private state: string;
    private form: FormGroup;
    private inst: Organization = new Organization();
    private parent = [];
    private parentOptions = [];
    private categories = [];
    private isSaving: boolean = false;

    @Input()
    set instance(inst: Organization) {
        if (!isUndefined(inst)) {
            this.inst = inst;
        }
    }

    @Input()
    set _state(state: string) {
        this.state = state;
    }

    constructor(private formBuilder: FormBuilder,
        private subject: NzModalSubject,
        private service: OrganizationService,
        private dataDictionaryService: DataDictionaryService) {
    }

    ngOnInit(): void {
        this.categories = this.dataDictionaryService.listByCategory("organization.category");
        this.form = this.formBuilder.group({
            parent: new FormControl(),
            category: new FormControl(null, [Validators.required]),
            name: new FormControl(null, [Validators.required]),
            code: new FormControl(null, [Validators.required]),
            address: new FormControl(),
            description: new FormControl()
        })
    }

    public save() {
        console.log(this.inst);
    }

    // codeRepeatValidator = (control: FormControl): { [s: string]: boolean } => {
    //     if (!control.value) {
    //         return { required: true };
    //     }
    //     let id = isUndefined(this.inst.id) ? "-1" : this.inst.id;
    //     if (!this.service.codeIsRepeat(control.value, id).isRepeat) {
    //         return { repeat: true, error: true };
    //     }
    // };

    getFormControl(name) {
        return this.form.controls[name];
    }

    cancel() {
        this.subject.destroy("onCancel");
    }

    // save() {
    //     this.isSaving = true;

    //     for (const i in this.form.controls) {
    //         this.form.controls[i].markAsDirty();
    //     }


    //     if (!this.form.invalid) {
    //         const len = this.parent.length;
    //         this.inst.parent = len == 0 ? -1 : this.parent[len - 1];
    //         if (this.state == "add") {
    //             this.service.save(this.inst)
    //                 .then(permission => {
    //                     this.subject.next(this.parent);
    //                     this.subject.destroy("onOk");
    //                 })
    //                 .catch(reason => CommonUtils.handleErrors(reason));
    //         } else if (this.state == "edit") {
    //             this.service.update(this.inst)
    //                 .then(permission => {
    //                     this.subject.destroy("onOk");
    //                 })
    //                 .catch(reason => CommonUtils.handleErrors(reason));
    //         }
    //     } else {
    //         this.isSaving = false;
    //     }
    // }

    // transformOptions(menus: Permission[]) {
    //     const options = [];
    //     menus.forEach(menu => options.push({
    //         ...menu,
    //         isLeaf: !menu.children || menu.children.length == 0,
    //         children: this.transformOptions(menu.children)
    //     }));
    //     return options;
    // }

}