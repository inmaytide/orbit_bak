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
      code: new FormControl(null, Validators.required)
    });
    this.service.listMenus()
      .then(data => this.parentOptions = this.transformOptions(data))
      .catch(reason => CommonUtils.handleErrors(reason))
  }

  getFormControl(name) {
    return this.form.controls[name];
  }

  cancel() {
    console.log(this.inst.parent);
    this.subject.destroy("onCancel");
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
