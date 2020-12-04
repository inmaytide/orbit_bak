import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {RequestToken, AuthenticateService} from 'src/app/core/passport/authenticate.service';
import {HttpClient} from '@angular/common/http';
import {NzMessageService} from 'ng-zorro-antd';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

const api = {
    login: '/uaa/oauth/token',
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

    neededCaptcha = false;

    loginButtonLoading = false;

    captcha = {};

    constructor(private fb: FormBuilder,
                private http: HttpClient,
                private router: Router,
                private message: NzMessageService,
                private service: AuthenticateService,
                private translate: TranslateService) {
        this.token = new RequestToken();
    }

    ngOnInit(): void {
        const passwordValidator = (control: FormControl): { [s: string]: boolean } => {
            if (!control.value) {
                return {required: true};
            } else if (control.value.length < 6) {
                return {length: true, error: true};
            }
            return {};
        };

        const captchaValidator = (control: FormControl): { [s: string]: boolean } => {
            if (this.neededCaptcha && !control.value) {
                return {required: true};
            }
            return {};
        };

        this.loginForm = this.fb.group({
            username: ['', [Validators.required]],
            password: ['', [passwordValidator]],
            captcha: ['', [captchaValidator]],
            remember: [false]
        });
    }


    refreshCaptcha() {
        this.http.get(api.getCaptcha).subscribe((res: any) => {
            this.captcha = res;
            this.token.captcha_name = res.captchaName;
            this.token.captcha = '';
        });
    }

    login() {
        this.loginButtonLoading = true;
        const body = new FormData();
        Object.keys(this.token).forEach(k => body.append(k, this.token[k]));
        this.http.post(api.login, body)
            .toPromise()
            .then(res => {
                this.service.storeToken(res);
                this.http.get(api.getUser(this.token.username)).subscribe(u => {
                    this.router.navigateByUrl('/');
                    this.service.storeUser(u);
                });
            })
            .catch(err => {
                if (err.error_description === 'err_bad_captcha') {
                    if (this.neededCaptcha) {
                        this.translate.get('error.bad_captcha').subscribe(res => this.message.error(res));
                    } else {
                        this.neededCaptcha = true;
                    }
                }
                if (this.neededCaptcha) {
                    this.refreshCaptcha();
                }
            })
            .finally(() => this.loginButtonLoading = false);
    }
}
