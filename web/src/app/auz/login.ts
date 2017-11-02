import {Component, OnInit} from "@angular/core";
import {Token} from "./models/token";

@Component({
  selector: "login",
  templateUrl: "./login.html",
  styleUrls: [
    "./login.css"
  ]
})
export class LoginComponent implements OnInit {

  private token: Token;

  ngOnInit(): void {
    this.token = new Token();
  }


}
