import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Permission} from "../../../models/permission";
import {NzModalSubject} from "ng-zorro-antd";
import {PermissionService} from "./permission.service";
import {CommonUtils} from "../../../common-utils";

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
        line-height: 30px;
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

  constructor(private formBuilder: FormBuilder,
              private subject: NzModalSubject,
              private service: PermissionService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      parent: new FormControl(),
      code: new FormControl(null, [Validators.required, this.codeRepeatValidator])
    });
    this.service.listMenus()
      .then(data => this.parentOptions = this.transformOptions(data))
      .catch(reason => CommonUtils.handleErrors(reason))
  }

  codeRepeatValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return {required: true};
    }
    if (this.service.codeIsRepeat(control.value, this.inst.id) == "false") {
      return {repeat: true, error: true};
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
  }

  transformOptions(menus: Permission[]) {
    const options = [];
    menus.forEach(menu => options.push({
      ...menu,
      isLeaf: !menu.children || menu.children.length == 0,
      children: this.transformOptions(menu.children)
    }));
    return options;
  }

}
