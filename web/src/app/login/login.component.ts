import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { RequestToken, AuthenticateService } from 'src/app/core/passport/authenticate.service';
import { HttpClient } from '@angular/common/http';
import { NzNotificationService } from 'ng-zorro-antd';
import { Router } from '@angular/router';

const api = {
    login: "/uaa/oauth/token",
    getUser: (username: string) => `/uaa/api/users/u/${username}`,
    getCaptcha: '/captcha'
};

@Component({
    selector: '/login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.less']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;

    token: RequestToken;

    neededCaptcha: Boolean = false;

    captcha: Object = {};

    constructor(private fb: FormBuilder,
        private http: HttpClient,
        private router: Router,
        private notification: NzNotificationService,
        private service: AuthenticateService) {
        this.token = new RequestToken();
    }

    ngOnInit(): void {
        this.loginForm = this.fb.group({
            username: ['', [Validators.required]],
            password: ['', [this.passwordValidator]],
            captcha: ['', [this.captchaValidator]],
            remember: [false]
        });
    }

    passwordValidator = (control: FormControl): { [s: string]: boolean } => {
        if (!control.value) {
            return { required: true };
        } else if (control.value.length < 6) {
            return { length: true, error: true };
        }
        return {};
    };

    captchaValidator = (control: FormControl): { [s: string]: boolean } => {
        if (this.neededCaptcha && !control.value) {
            return { required: true };
        }
        return {};
    }

    refreshCaptcha() {
        this.http.get(api.getCaptcha)
            .subscribe(res => {
                this.captcha = res;
                this.token.captcha_name = res['captchaName'];
                this.token.captcha = "";
            });
    }

    login($event: any) {
        $event.preventDefault();

        const body = new FormData();
        Object.keys(this.token).forEach(k => body.append(k, this.token[k]));
        this.http.post(api.login, body)
            .toPromise()
            .then(res => {
                this.service.storeToken(res);
                this.http.get(api.getUser(this.token.username))
                    .subscribe(u => {
                        this.service.storeUser(u);
                        this.router.navigateByUrl("/");
                    })
            })
            .catch(err => {
                if (err.error_description === "err_bad_captcha") {
                    if (this.neededCaptcha) {
                        this.notification.warning("系统提醒", "验证码输入错误");
                    } else {
                        this.neededCaptcha = true;
                    }
                }

                if (this.neededCaptcha) {
                    this.refreshCaptcha();
                }
            });
    }
}
