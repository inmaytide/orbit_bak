import {Component, OnInit} from "@angular/core";
import {User} from "../../models/user";
import {CommonUtils} from "../../common-utils";
import {GlobalVariables} from "../../global-variables";
import {Router} from "@angular/router";

@Component({
  selector: 'main-top',
  templateUrl: './main-top.html',
  styleUrls: [
    './main-top.css'
  ]
})
export class MainTopComponent implements OnInit{

  private user: User;

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.user = CommonUtils.getPrincipal();
  }

  logout() {
    localStorage.removeItem(GlobalVariables.PRINCIPAL);
    this.router.navigateByUrl("login")
      .catch(reason => console.log(reason));
  }

}
