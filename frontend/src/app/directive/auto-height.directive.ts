import {Directive, HostBinding} from "@angular/core"

@Directive ({
  selector: '[auto-height]'
})

export class AutoHeightDirective {
  @HostBinding("style.minHeight.px") minHeight = window.innerHeight - 49;
}
