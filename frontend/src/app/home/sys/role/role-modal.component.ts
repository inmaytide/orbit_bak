import {Component, OnInit, ViewChild} from "@angular/core";
import {NgbActiveModal, NgbModal, NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {RoleService} from "./role.service";
import {Role} from "../../../models/role-model";
import {isUndefined} from "util";
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {Commons} from "../../../commons";
import {PermissionService} from "../permission/permission.service";
import {Permission} from "../../../models/permission-model";
import {MTreeNode} from "../../../m-tree/m-tree-node";
import {UserSelectModalComponent} from "../user/user-select-modal.component";
import {User} from "../../../models/user-model";

@Component({
  selector: "role-modal",
  templateUrl: "./role-modal.component.html",
  styleUrls: ['./role-modal.css']
})
export class RoleModalComponent implements OnInit {

  public role: Role = new Role();
  public permissionIds: string[] = [];
  public state = 'add';
  public nodes: MTreeNode[];

  @ViewChild("tipCode") public tipCode: NgbTooltip;
  @ViewChild("tipName") public tipName: NgbTooltip;

  constructor(private activeModal: NgbActiveModal,
              private service: RoleService,
              private translate: TranslateService,
              private router: Router,
              private modalService: NgbModal,
              private permissionService: PermissionService) {
  }

  ngOnInit(): void {
    this.role.permissions.forEach(permission => this.permissionIds.push(permission.id));

    this.permissionService.getData()
      .subscribe(
        response => this.nodes = this.generateTreeNodes(response),
        error => Commons.errorHandler(error, this.router, this.modalService)
      );
  }

  public generateTreeNodes(data: Permission[]) {
    const nodes = [];
    data.forEach(permission => {
      nodes.push({
        id: permission.id,
        name: permission.name + "  ( " + permission.code + " )",
        spread: false,
        selected: this.permissionIds.indexOf(permission.id) != -1,
        children: this.generateTreeNodes(permission.children)
      })
    });
    return nodes;
  }

  public save() {
    if (!this.checkCode() || !this.checkName()) {
      return;
    }
    this.service.add(this.role)
      .then(role => {
        this.role = role;
        this.state = "lock";
      })
      .catch(reason => Commons.errorHandler(reason, this.router, this.modalService))
  }

  public remove() {
    Commons.confirm(this.modalService, this.translate.getParsedResult({}, "role.remove.confirm"))
      .then(result => {
        console.log(result);
        if (result) {
          this.service.remove([this.role.id])
            .then(response => this.activeModal.close())
            .catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
        }
      });
  }

  private findSelectedNode(nodes: MTreeNode[], permissionIds: string[]) {
    nodes.forEach(node => {
      if (node.selected == true) {
        permissionIds.push(node.id);
        this.findSelectedNode(node.children, permissionIds);
      }
    })
  }

  public savePermissions() {
    let permissionIds = [];
    this.findSelectedNode(this.nodes, permissionIds);
    if (permissionIds.length == 0) {
      Commons.error(this.modalService, this.translate.getParsedResult({}, "role.modal.tabs.permission.save.no.selected"));
    }
    this.service
      .associatePermissions(permissionIds.join(","), this.role.id)
      .then(response => Commons.success(this.modalService, this.translate.getParsedResult({}, "role.modal.tabs.permission.save.success")))
      .catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

  public addUsers() {
    let modelRef = this.modalService.open(UserSelectModalComponent, {size: 'lg', backdrop: 'static'});
    modelRef.result.then(result => {
      let users = result as User[];
      if (users.length == 0) {

        return;
      }
      this.service.associateUsers(users, this.role.id)
        .then(response => {
          console.log(response);
        }).catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
    }).catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

  public close() {
    this.activeModal.close();
  }

  public checkCode(): boolean {
    this.tipCode.close();
    if (isUndefined(this.role.code) || this.role.code.trim() === "") {
      this.tipShow(this.tipCode, this.translate.getParsedResult({}, "role.validator.code.not.empty"));
      return false;
    }
    if (!this.service.checkCode(isUndefined(this.role.id) || this.role.id == null || this.role.id == "" ? "-1" : this.role.id, this.role.code)) {
      this.tipShow(this.tipCode, this.translate.getParsedResult({}, "role.validator.code.not.repeat"));
      return false;
    }
    return true;
  }

  public checkName(): boolean {
    if (isUndefined(this.role.name) || this.role.name == null || this.role.name.trim() === "") {
      this.tipShow(this.tipName, this.translate.getParsedResult({}, "role.validator.name.not.empty"));
      return false;
    }
    return true;
  }

  private tipShow(tip, message) {
    tip.open({message: message});
    setTimeout(() => tip.close(), 2000);
  }

}
