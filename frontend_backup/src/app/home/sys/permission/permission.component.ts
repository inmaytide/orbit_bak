import {Component, Input, OnInit} from "@angular/core"
import {Permission} from "../../../models/permission-model";
import 'rxjs/add/operator/map'
import {PermissionService} from "./permission.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PermissionModalComponent} from "./permission-modal.component";
import {Router} from "@angular/router";
import {Commons} from "../../../commons";

@Component({
  selector: "permission",
  templateUrl: "./permission.component.html",
  styleUrls: ["./permission.css"]
})

export class PermissionComponent implements OnInit {

  public permissions: Permission[] = [];

  public constructor(public service: PermissionService,
                     public modalService: NgbModal,
                     public router: Router) {
  }

  ngOnInit(): void {
    this.queryList();
  }

  public queryList() {
    this.service.getData().subscribe(
      response => this.permissions = response,
      error => Commons.errorHandler(error, this.router, this.modalService)
    );
  }

  public remove() {
    let actives = [];
    this.service.getActives(actives, this.permissions);
    //未选择数据
    if (actives.length == 0) {
      Commons.error(this.modalService, "请选择要删除的数据");
      return;
    }

    //选择的数据有子菜单
    for (let i = 0; i < actives.length; i++) {
      if (actives[i].children.length != 0) {
        Commons.error(this.modalService, "请先删除子菜单");
        return;
      }
    }

    Commons.confirm(this.modalService, "确认要删除当前选中的数据吗").then(result => {
      if (result == true) {
        this.service.remove(actives).then(data => {
          actives.forEach(active => {
            let _data = this.getSameLevel(this.permissions, active.parent);
            _data.splice(_data.indexOf(active), 1);
          });
        }).catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
      }
    });
  }

  add() {
    let actives = [];
    this.service.getActives(actives, this.permissions);

    const modalRef = this.modalService.open(PermissionModalComponent, {
      backdrop: 'static',
      size: 'lg'
    });
    modalRef.componentInstance.parent = actives[0] || {id: "-1", name: "GENERAL"};
    modalRef.componentInstance.permission.parent = (actives[0] && actives[0].id) || "-1";
    modalRef.componentInstance.permission.category = "MENU";
    modalRef.componentInstance.data = this.permissions;
  }

  public move(t) {
    let actives = [];
    this.service.getActives(actives, this.permissions);
    if (actives.length == 0) {
      Commons.error(this.modalService, "请选择要移动的菜单");
      return;
    } else if (actives.length > 1) {
      Commons.error(this.modalService, "一次只能操作条数据");
      return;
    }

    let selected = actives[0];
    let sameLevelPermissions = this.getSameLevel(this.permissions, selected.parent);
    if (sameLevelPermissions.length == 1) {
      return;
    }

    let index = sameLevelPermissions.indexOf(selected);
    let otherIndex = t === "up" ? this.getPrev(sameLevelPermissions, index)
      : this.getNext(sameLevelPermissions, index);
    let other = sameLevelPermissions[otherIndex];
    this.service.changeSort(selected.id, t)
      .then(result => {
        sameLevelPermissions[index] = other;
        sameLevelPermissions[otherIndex] = selected;
      })
      .catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

  private getPrev(data: Permission[], index: number): number {
    return index === 0 ? data.length - 1 : index - 1;
  }

  private getNext(data: Permission[], index: number): number {
    return index === data.length - 1 ? 0 : index + 1;
  }

  private getSameLevel(data: Permission[], parent: string): Permission[] {
    if (parent == "-1") {
      return data;
    }
    let children = [];
    for (let i = 0; i < data.length; i++) {
      if (data[i].id === parent) {
        return data[i].children;
      }
      data[i].children.forEach(i => children.push(i));
    }
    return this.getSameLevel(children, parent);
  }

}
