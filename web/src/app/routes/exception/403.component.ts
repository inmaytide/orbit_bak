import {Component} from '@angular/core';

@Component({
    selector: 'exception-403',
    template: `
        <div class="exception">
            <div class="content _403">
                <div>
                    <div>403</div>
                    <div>{{'error.403' | translate}}</div>
                    <div>
                        <button nz-button [nzType]="'primary'">{{'btn.back_to_home' | translate}}</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    styleUrls: ['./exception.less']
})
export class Exception403Component {

}
