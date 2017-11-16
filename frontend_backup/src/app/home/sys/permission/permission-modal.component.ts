import {Component, OnInit} from "@angular/core";
import {Permission} from "../../../models/permission-model";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PermissionService} from "./permission.service";
import * as GlobalVariable from "../../../globals";
import {Commons} from "../../../commons";
import {Router} from "@angular/router";

@Component({
  selector: 'permission-modal',
  templateUrl: './permission-modal.component.html',
  styleUrls: ['./permission.modal.css']
})

export class PermissionModalComponent implements OnInit {

  public state = "add";
  public parent: Permission;
  public permission: Permission = new Permission();
  public data: Permission[];
  public categories = [];
  public _categories = GlobalVariable.MENU_CATEGORIES;
  public icons = ['home', 'cog', 'ravelry'];
  private cache: Permission;

  constructor(public activeModal: NgbActiveModal,
              public service: PermissionService,
              public router: Router,
              public modalService: NgbModal) {
  }

  ngOnInit(): void {
    for (let key in this._categories) {
      if (this._categories.hasOwnProperty(key)) {
        this.categories.push({value: key, text: this._categories[key]});
      }
    }
  }

  close() {
    if (this.state == "edit") {
      this.permission = Object.assign(this.permission, this.cache);
      this.state = 'lock';
    } else {
      this.activeModal.dismiss();
    }
  }

  changeState(_state) {
    if (_state == "edit") {
      this.cache = Object.assign({}, this.permission);
    }
    this.state = _state;
  }

  save(form) {
    if (!form.valid) {
      return;
    }
    if (this.state == 'add') {
      this.add();
    } else if (this.state == 'edit') {
      this.edit();
    }
  }

  remove() {
    if (this.permission.children.length > 0) {
      Commons.error(this.modalService, "请先删除子菜单");
      return;
    }
    Commons.confirm(this.modalService, "确认删除").then(result => {
      if (result) {
        this.service.remove([this.permission]).then(result => {
          this.activeModal.close();
          this.service.getData().subscribe(
            response => this.data = response.data,
            error => Commons.errorHandler(error, this.router, this.modalService)
          );
        }).catch(reason => {
          Commons.errorHandler(reason, this.router, this.modalService);
        })
      }
    })
  }

  private edit() {
    this.service.update(this.permission).then(permission => {
      this.permission = Object.assign(this.permission, permission);
      this.activeModal.close();
    }).catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

  private add() {
    this.service.add(this.permission).then(permission => {
      this.activeModal.close();
      if (this.parent.id == "-1") {
        this.data.push(permission);
      } else {
        this.parent.children.push(permission);
      }
      this.parent.spread = true;
    }).catch(reason => {
      this.activeModal.close();
      Commons.errorHandler(reason, this.router, this.modalService);
    })
  }

}
