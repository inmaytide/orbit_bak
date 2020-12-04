import {Component} from '@angular/core';

@Component({
    selector: 'exception-404',
    template: `
        <div class="exception">
            <div class="content _404">
                <div>
                    <div>404</div>
                    <div>{{'error.404' | translate}}</div>
                    <div>
                        <button nz-button [nzType]="'primary'">{{'btn.back_to_home' | translate}</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    styleUrls: ['./exception.less']
})
export class Exception404Component {

}
