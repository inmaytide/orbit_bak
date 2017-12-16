import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Permission } from "../../../models/permission";
import { NzModalSubject } from "ng-zorro-antd";
import { PermissionService } from "./permission.service";
import { CommonUtils } from "../../../common-utils";
import { DataDictionary } from "../../../models/data-dictionary";
import { DataDictionaryService } from "../data-dictionary/data-dictionary.service";
import { isUndefined } from "util";

@Component({
    selector: "permission-info",
    templateUrl: './permission.info.html',
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
      }ÒÒÒ
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
export class PermissionInfoComponent implements OnInit {

    private form: FormGroup;
    private inst: Permission = new Permission();
    private parent = [];
    private parentOptions = [];
    private categories: DataDictionary[] = [];
    private methods: DataDictionary[] = [];

    constructor(private formBuilder: FormBuilder,
        private subject: NzModalSubject,
        private service: PermissionService,
        private dataDictionaryService: DataDictionaryService) {
    }

    ngOnInit(): void {
        this.categories = this.dataDictionaryService.listByCategory("permission.category");
        this.methods = this.dataDictionaryService.listByCategory("permission.method");
        this.form = this.formBuilder.group({
            parent: new FormControl(),
            code: new FormControl(null, [Validators.required, this.codeRepeatValidator]),
            name: new FormControl(null, [Validators.required]),
            category: new FormControl(null, [Validators.required]),
            icon: new FormControl(),
            action: new FormControl(),
            description: new FormControl(),
            method: new FormControl(null, [Validators.required])
        });
        this.service.listMenus()
            .then(data => this.parentOptions = this.transformOptions(data))
            .catch(reason => CommonUtils.handleErrors(reason));
    }

    codeRepeatValidator = (control: FormControl): { [s: string]: boolean } => {
        if (!control.value) {
            return { required: true };
        }
        let id = isUndefined(this.inst.id) ? "-1" : this.inst.id;
        if (!this.service.codeIsRepeat(control.value, id).isRepeat) {
            return { repeat: true, error: true };
        }
    };

    getFormControl(name) {
        return this.form.controls[name];
    }

    cancel() {
        this.subject.destroy("onCancel");
    }

    save() {
        for (const i in this.form.controls) {
            this.form.controls[i].markAsDirty();
        }


        if (!this.form.invalid) {
            console.log(this.parent);
            this.inst.parent = this.parent.length == 0 ? -1 : this.parent.pop();
            console.log(this.inst)
            this.service.save(this.inst)
                .then(permission => this.cancel)
                .catch(reason => CommonUtils.handleErrors(reason));
        }
    }

    transformOptions(menus: Permission[]) {
        const options = [];
        menus.forEach(menu => options.push({
            ...menu,
            isLeaf: !menu.children || menu.children.length == 0,
            children: this.transformOptions(menu.children)
        }));
        console.log(options);
        return options;
    }

}
