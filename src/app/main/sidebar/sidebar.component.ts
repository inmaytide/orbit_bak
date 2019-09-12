import {Component, Input} from '@angular/core';
import {AuthenticateService} from '../../core/passport/authenticate.service';
import {MainService} from '../main.service';

@Component({
    selector: 'sidebar',
    template: `
        <div class="profile">
            <nz-avatar nzIcon="user" nzSize="large" [nzSrc]="avatar"></nz-avatar>
            <div class="base">
                <div>{{user.name}}</div>
                <div>{{user.email}}</div>
            </div>
        </div>
        <sidebar-nav></sidebar-nav>
    `
})
export class SidebarComponent {
    avatar = 'http://192.168.2.126:80/group1/M00/25/FF/wKgCfl13hZKAZLc9AAAdAfjaBLs25.jpeg';

    user: any;

    constructor(private service: AuthenticateService) {
        this.user = service.getUser();
    }
}
