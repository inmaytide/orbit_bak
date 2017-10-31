import {MPage} from "../models/m-page-model";
import {MColumnModel} from "../models/m-column-model";
import {Component, Input, OnInit} from "@angular/core";

@Component({
  selector: "m-table",
  templateUrl: "./m-table.html"
})
export class MTableComponent implements OnInit {


  @Input() private page: MPage<any>;
  @Input() private models: MColumnModel[] = [];
  @Input() private checkbox: boolean = true;
  @Input() private serialNumber: boolean = false;

  private allSelected: boolean = false;

  ngOnInit(): void {

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

  public getContent() : any[] {
    this.page.content.forEach(item => item.selected = item.selected || false);
    return this.page.content;
  }
}
