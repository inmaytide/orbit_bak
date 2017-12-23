import {Component, OnInit} from "@angular/core";
import {Token} from "./models/token";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NzMessageService} from "ng-zorro-antd";
import {GlobalVariables} from "../global-variables";

@Component({
  selector: "login",
  templateUrl: "./login.html",
  styleUrls: [
    "./login.css"
  ]
})
export class LoginComponent implements OnInit {

  private token: Token;
  private validateForm: FormGroup;
  private captchaImagePath: string;

  constructor(private service: LoginService,
              private router: Router,
              private fb: FormBuilder,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.token = new Token();
    this.getCaptcha();
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]]
    });
  }

  public login() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
    }
    if (!this.validateForm.invalid) {
      this.service.login(this.token)
        .then(() => this.router.navigateByUrl("/index"))
        .catch(reason => this.message.create("error", reason.error.message));
    }
  }

  public getCaptcha() {
    this.service.getCaptcha()
    .then(image => this.captchaImagePath = "data:image/jpeg;base64," + image)
    .catch(reason => this.message.create("error", reason.error.message));
  }
}
