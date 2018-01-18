import { Component, Input } from "@angular/core";


@Component({
    selector: "breadcrumb",
    template: `
        <div class="bar-position">
            <nz-breadcrumb nzSeparator=">">
                <i class="fa fa-map-marker"></i>
                <nz-breadcrumb-item *ngFor="let path of paths">
                    {{path}}
                </nz-breadcrumb-item>
            </nz-breadcrumb>
        </div>
    `
})
export class BreadcrumbComponent {
    @Input()
    public paths: string[]
}