import {Component, OnInit} from "@angular/core";
import {trigger, state, style, animate, transition} from '@angular/animations';
import * as GlobalVariable from "../../globals";
import {Router} from "@angular/router";
import {User} from "../../models/user-model";
import {Commons} from "../../commons";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient} from "@angular/common/http";
import {PermissionService} from "../sys/permission/permission.service";

@Component({
  selector: 'side-bar',
  templateUrl: './sidebar.component.html',
  animations: [
    trigger("tiggerMenu", [
      state("inactive", style({transform: 'scaleY(0)'})),
      state("active", style({transform: 'scaleY(1)'})),
      transition('inactive => active', animate('200ms ease-in')),
      transition('active => inactive', animate('200ms ease-out'))
    ])
  ]
})

export class SidebarComponent implements OnInit {
  public images: string = GlobalVariable.PATH_IMAGES;
  public menus = [];
  public user: User;
  public active;
  public p_active;

  public constructor(private http: HttpClient,
                     private router: Router,
                     private modalSerivce: NgbModal,
                     private menuSerivce: PermissionService) {
  }

  ngOnInit(): void {
    let objUser = localStorage.getItem(GlobalVariable.CURRENT_USER);
    this.user = JSON.parse(objUser);
    this.menuSerivce.findUserMenus()
      .then(menus => {
        if (menus.length > 0) {
          this.menus = menus;
          this.menus.forEach(menu => menu.state = "inactive");
          this.active = this.menus[0];
          this.active.state = 'active';
          this.p_active = this.active.id;
        }
      }).catch(reason => Commons.errorHandler(reason, this.router, this.modalSerivce));
  }

  changeActive(menu, $event) {
    if (menu.id == this.active.id) {
      this.active.state = this.active.state == 'inactive' ? 'active' : 'inactive';
    } else {
      this.p_active = menu.id;
      this.active.state = 'inactive';
      menu.state = "active";
      this.active = menu;
    }
  }

  changeContent(menu, $event) {
    $event.stopPropagation();
    this.router.navigateByUrl(menu.action);
  }

  triggerMenuDone($event, menu) {
    if (menu.state == "inactive") {
      $event.element.style.display = 'none';
    }
  }

  triggerMenuStart($event, menu) {
    if (menu.state == "active") {
      $event.element.style.display = 'block';
    }
  }

}
