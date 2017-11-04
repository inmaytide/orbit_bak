import {Component, Input, OnInit} from "@angular/core";
import {GlobalVariables} from "../../global-variables";
import {Router} from "@angular/router";
import {PermissionService} from "../sys/permission/permission.service";
import {User} from "../../models/user";
import {CommonUtils} from "../../common-utils";
import {Permission} from "../../models/permission";

@Component({
  selector: 'side-bar',
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent implements OnInit {
  private images: string = GlobalVariables.IMAGES_BASE_PATH;
  private menus: Permission[] = [];
  private user: User;
  @Input() collapsed = false;

  public constructor(private router: Router,
                     private service: PermissionService) {
  }

  ngOnInit(): void {
    this.user = CommonUtils.getPrincipal();
    this.service.findUserMenus()
      .then(menus => this.menus = menus)
      .catch(reason => console.log(reason));
  }

  changeContent(menu, $event) {
    $event.stopPropagation();
    this.router.navigateByUrl(menu.action);
  }

}
