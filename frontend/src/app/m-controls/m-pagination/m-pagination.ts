import {Component, EventEmitter, Input, Output} from "@angular/core";
import {MPage} from "../models/m-page-model";

@Component({
  selector: "m-pagination",
  templateUrl: "./m-pagination.html"
})
export class MPaginationComponent {

  @Input() page: MPage<any>;

  @Output() pageChange: EventEmitter<any> = new EventEmitter<any>();

  pageChanged(event) {
    if (!isFinite(event) && event != 'size') {
      return;
    }
    let emit = {number: event == 'size' ? 1 : event, size: this.page.size};
    this.pageChange.emit(emit);
  }

}
