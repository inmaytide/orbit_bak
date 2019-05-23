import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';


const KEY_USER = "__ORBIT_USER__";
const KEY_ACCESS_TOKEN = "__ORBIT_TOKEN__";
const KEY_REFRESH_TOKEN = "__ORBIT_REFRESH_TOKEN__";
const KEY_EXPIRES = "__ORBIT_TOKEN_EXPIRED__"

export class AuthenticateService {

    isAuthenticated() {
        const value = sessionStorage.getItem(KEY_USER);
        return value !== null;
    }

    getAccessToken() {
        return sessionStorage.getItem(KEY_ACCESS_TOKEN) || "";
    }

    isExpired() {
        if (!this.isAuthenticated()) {
            return true;
        }
        const expires = JSON.parse(sessionStorage.getItem(KEY_EXPIRES));
        const now = new Date().getTime();
        return (now - expires.timestamp) >= expires.expires_in;
    }

    storeToken(token: any) {
        sessionStorage.setItem(KEY_ACCESS_TOKEN, token.access_token)
        sessionStorage.setItem(KEY_REFRESH_TOKEN, token.refresh_token)
        sessionStorage.setItem(KEY_EXPIRES, JSON.stringify({
            expires_in: token.expires_in,
            timestamp: new Date().getTime()
        }))
    }

    storeUser(user: any) {
        sessionStorage.setItem(KEY_USER, JSON.stringify(user));
    }

}

@Injectable()
export class AuthenticateFilter implements CanActivate {
    constructor(public router: Router,
        private service: AuthenticateService) {

    }

    canActivate(): boolean {
        if (this.service.isAuthenticated() || !this.service.isExpired()) {
            return true;
        }
        this.router.navigate(["login"]);
        return false;
    }
}

export class RequestToken {
    grant_type: string = "password";
    client_id: string = "orbit";
    scope: string = "all";
    client_secret: string = "59a84cbf83227a35";
    username: string;
    password: string;
    refresh_token: string;
    captcha: string;
    captcha_name: string;
}
