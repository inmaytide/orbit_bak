import {Component, Input, OnInit} from "@angular/core";
import {Permission} from "../../../models/permission-model";
import {PermissionService} from "./permission.service";
import {PermissionModalComponent} from "./permission-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import * as GlobalVariable from "../../../globals";

@Component({
  selector: 'permission-child-view',
  templateUrl: './permission-child-view.component.html',
  styleUrls: ['./permission-child-view.css']
})

export class PermissionChildViewComponent implements OnInit {

  @Input()
  public permissions: Permission[];

  @Input()
  public parent;

  @Input()
  public level: number;

  public levelClass;

  public category = GlobalVariable.MENU_CATEGORIES;

  public constructor(public service: PermissionService,
                     public modalService: NgbModal,
                     public router: Router) {

  }

  ngOnInit(): void {
    this.levelClass = 'level-' + this.level;
  }

  change(inst, event) {
    event.stopPropagation();
    inst.spread = !inst.spread;
    if (!inst.spread) {
      this.collapse(inst);
    }
  }

  collapse(permission: Permission) {
    permission.children.forEach(p => {
      p.spread = false;
      this.collapse(p);
    });
  }

  selectPermission(inst, event) {
    inst.state = inst.state === 'active' ? null : 'active';
  }


  edit(inst, event) {
    event.stopPropagation();
    const modalRef = this.modalService.open(PermissionModalComponent, {
      backdrop: 'static',
      size: 'lg'
    });
    modalRef.componentInstance.parent = this.parent || {id: "-1", name: "GENERAL"};
    modalRef.componentInstance.permission = inst;
    modalRef.componentInstance.state = 'lock';
  }
}
