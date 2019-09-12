import {Component} from '@angular/core';
import {MainService} from './main.service';

@Component({
    selector: 'main',
    template: `
        <div class="main">
            <div class="header">
                <main-header></main-header>
            </div>
            <div [ngClass]="{sidebar: true, collapsed: service.isCollapsed}">
                <sidebar></sidebar>
            </div>
            <div [ngClass]="{content: true, collapsed: service.isCollapsed}">
                <router-outlet></router-outlet>
            </div>
        </div>
    `
})
export class MainComponent {
    constructor(private service: MainService) {
    }
}
