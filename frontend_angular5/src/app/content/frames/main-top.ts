import {Component, OnInit} from "@angular/core";
import {User} from "../../models/user";
import {CommonUtils} from "../../common-utils";
import {GlobalVariables} from "../../global-variables";
import {Router} from "@angular/router";
import { isUndefined } from "util";

@Component({
  selector: 'main-top',
  templateUrl: './main-top.html',
  styleUrls: [
    './main-top.css'
  ]
})
export class MainTopComponent implements OnInit{

  private user: User = new User();

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.setUser(this.user);
  }

  setUser(user) {
    user = Object.assign(user, CommonUtils.getPrincipal());
    if (!user.id) {
      setTimeout(() => this.setUser(user), 2000);
    }
  }

  logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
    this.router.navigateByUrl("login")
      .catch(reason => console.log(reason));
  }

}
