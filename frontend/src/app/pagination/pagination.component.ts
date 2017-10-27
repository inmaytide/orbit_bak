import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Page} from "../models/page-model";

@Component({
  selector: "pagination",
  templateUrl: "./pagination.component.html"
})
export class PaginationComponent {

  @Input() page: Page<any>;

  @Output() pageChange: EventEmitter<any> = new EventEmitter<any>();

  pageChanged(event) {
    if (!isFinite(event) && event != 'size') {
      return;
    }
    let emit = {number: event == 'size' ? 1 : event, size: this.page.size};
    this.pageChange.emit(emit);
  }

}
