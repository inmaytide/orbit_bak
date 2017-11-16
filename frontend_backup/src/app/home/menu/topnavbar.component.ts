import {Component, Injectable, OnInit} from "@angular/core";
import * as GlobalVariable from "../../globals";
import {HomeComponent} from "../home.component";
import {LoginService} from "../../auth/login/login.service";
import {Router} from "@angular/router";
import 'rxjs/add/operator/map';
import {User} from "../../models/user-model";

@Component({
  selector: "top-navbar",
  templateUrl: "./topnavbar.component.html"
})

@Injectable()
export class TopnavbarComponent implements OnInit {

  public images = GlobalVariable.PATH_IMAGES;

  public user: User;

  constructor(public homeComponent: HomeComponent,
              public loginService: LoginService,
              public router: Router) {

  }

  ngOnInit(): void {
    let objUser = localStorage.getItem(GlobalVariable.CURRENT_USER);
    this.user = JSON.parse(objUser);
  }

  menuToggle() {
    this.homeComponent.changeMenuStyle();
  }

  public logout() {
    this.loginService.logout();
    this.router.navigateByUrl("login");
  }
}
