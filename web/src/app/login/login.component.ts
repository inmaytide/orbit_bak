import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestToken} from '../utils/authenticate';
import {HttpClient} from '@angular/common/http';
import {NzNotificationService} from 'ng-zorro-antd';

const api = {
  login: "/uaa/oauth/token"
};

@Component({
    selector: '/login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.less']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  token: RequestToken;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private notification: NzNotificationService) {
    this.token = new RequestToken();
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }


  login() {
    const body = new FormData();
    Object.keys(this.token).forEach(k => body.append(k, this.token[k]));
    this.http.post(api.login, body)
      .subscribe(
        res => {

        },
        err => {
          console.log(err);
          if (err.status === 403) {
            if (err.error.error_description === "err_bad_credentials") {
              this.notification.create('warning', '系统提示', '用户名或密码输入错误');
            } else if (err.error.error_description.startWith("err_login_restricted")) {
              const limit = err.error.error_description.replace("err_login_restricted_");
              if (limit === "-1") {
                this.notification.create("error", '系统提示', "用户名或密码错误次数过多，请联系管理员")
              }
              this.notification.create("error", '系统提示', "用户名或密码错误次数过多，请${limit}分钟后再试")
            }
          } else if (err.status === 503) {
            if (err.error.error === "Service Unavailable") {
              this.notification.create('warning', '系统提示', '系统服务不可用，请联系管理员');
            }
          }
        }
      );
  }
}
