import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthenticateUtils} from './utils/authenticate';

@Injectable()
export class AuthenticateService implements CanActivate {
  constructor(public router: Router) {

  }

  canActivate(): boolean {
    if (AuthenticateUtils.isAuthenticated()) {
      return true;
    }
    this.router.navigate(["login"]);
    return false;
  }
}
