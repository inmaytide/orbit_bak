import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MainService} from '../main.service';

@Component({
    selector: 'main-header',
    templateUrl: './header.component.html'
})
export class HeaderComponent {

    constructor(private service: MainService) {
    }
}
