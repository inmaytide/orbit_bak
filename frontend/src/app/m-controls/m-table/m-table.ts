import {MPage} from "../models/m-page-model";
import {MColumnModel} from "../models/m-column-model";
import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {isUndefined} from "util";

@Component({
  selector: "m-table",
  templateUrl: "./m-table.html",
  styleUrls: [
    "./m-table.css"
  ]
})
export class MTableComponent implements OnInit {


  @Input() private page: MPage<any>;
  @Input() private models: MColumnModel[] = [];
  @Input() private checkbox: boolean = true;
  @Input() private serialNumber: boolean = false;
  @Output() pageChange: EventEmitter<any> = new EventEmitter<any>();

  private allSelected: boolean = false;

  ngOnInit(): void {

  }

  public pageChanged(emit) {
    this.pageChange.emit(emit);
  }

  public countColumns(): number {
    let displayedColumns = this.models.filter(model => isUndefined(model.display) || model.display);
    return displayedColumns.length + (this.checkbox ? 1 : 0) + (this.serialNumber ? 1 : 0);
  }

  public selectAll() {
    this.allSelected = !this.allSelected;
    this.page.content.forEach(item => item.selected = this.allSelected);
  }

  public selectItem(item) {
    console.log(item.selected);
    item.selected = !item.selected;
    this.allSelected = item.selected && this.page.content.filter(item => item.selected).length == this.page.content.length;
  }

  public getContent(): any[] {
    this.page.content.forEach(item => item.selected = item.selected || false);
    return this.page.content;
  }
}
