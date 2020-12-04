import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticateService} from '../../core/passport/authenticate.service';
import {MainService} from '../main.service';

const api = {
    getMenusByUser: (id) => `/uaa/api/users/${id}/web-menus`
};

@Component({
    selector: 'sidebar-nav',
    template: `
        <ul nz-menu nzMode="inline" style="margin-top: 15px; border-right: none;" [nzInlineCollapsed]="mainService.isCollapsed">
            <ng-container *ngTemplateOutlet="menutpl, context: {$implicit: menus}"></ng-container>
            <ng-template #menutpl let-menus>
                <ng-container *ngFor="let m of menus; let i = index">
                    <li *ngIf="m.children && m.children.length !== 0"
                        nz-submenu
                        [nzIcon]="m.icon"
                        [nzTitle]="m.name"
                        [nzPaddingLeft]="m.level * 24"
                        [(nzOpen)]="m.expand"
                        (nzOpenChange)="openHandler(menus, i)">
                        <ul>
                            <ng-container *ngTemplateOutlet="menutpl, context: {$implicit: m.children}"></ng-container>
                        </ul>
                    </li>
                    <li *ngIf="!m.children || m.children.length === 0" nz-menu-item [nzPaddingLeft]="m.level * 24">
                        {{m.name}}
                    </li>
                </ng-container>
            </ng-template>
        </ul>
    `
})
export class NavComponent implements OnInit {
    menus = [];

    constructor(private http: HttpClient,
                private service: AuthenticateService,
                private mainService: MainService) {

    }

    ngOnInit(): void {
        this.http.get(api.getMenusByUser(this.service.getUser().id)).subscribe((res: any) => this.menus = res);
    }

    openHandler(menus, index) {
        menus.forEach((m, i) => {
            if (i !== index) {
                m.expand = false;
            }
        });
    }
}
