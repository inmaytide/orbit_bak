import {Component, Input, OnInit} from "@angular/core";
import {GlobalVariables} from "../../global-variables";
import {Router} from "@angular/router";
import {PermissionService} from "../sys/permission/permission.service";
import {Permission} from "../../models/permission";
import {CommonUtils} from "../../common-utils";

@Component({
  selector: 'side-bar',
  templateUrl: './sidebar.component.html',
  styles: [
      `
        .fa {
            font-size: 16px;
            margin-left: -8px;
            padding-right: 5px;
        }
      `
  ]
})
export class SidebarComponent implements OnInit {
  private images: string = GlobalVariables.IMAGES_BASE_PATH;
  private menus: Permission[] = [];
  @Input() collapsed = false;

  public constructor(private router: Router,
                     private service: PermissionService) {
  }

  ngOnInit(): void {
    this.service.findUserMenus(CommonUtils.getPrincipal().username)
      .then(menus => this.menus = menus)
      .catch(reason => CommonUtils.handleErrors(reason));
  }

  changeContent(menu, $event) {
    $event.stopPropagation();
    this.router.navigateByUrl(menu.action);
  }

}
