import {Component, OnInit} from "@angular/core";
import {LogService} from "./log.service";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Commons} from "../../../commons";
import {Log} from "../../../models/log-model";
import {Page} from "../../../models/page-model";
import {saveAs} from "file-saver";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.css']
})
export class LogComponent implements OnInit {

  public page: Page<Log> = new Page<Log>();

  public conditions;

  constructor(public logService: LogService,
              public router: Router,
              public translate: TranslateService,
              public modalService: NgbModal) {

  }

  ngOnInit(): void {
    this.resetConditions();
    this.loadData(1, 10, {});
  }

  private getConditions() {
    let _conditions = {};
    if (this.conditions.keywords != '') {
      _conditions['keywords'] = this.conditions.keywords;
    }
    if (this.conditions.begin != undefined) {
      let begin = this.conditions.begin;
      _conditions['begin'] = [begin.year, begin.month, begin.day].join("-");
    }
    if (this.conditions.end != undefined) {
      let end = this.conditions.end;
      _conditions['end'] = [end.year, end.month, end.day].join("-");
    }
    return _conditions;
  }

  public pageChange(pageable) {
    this.loadData(pageable.number, pageable.size, this.getConditions());
  }

  search(event) {
    if (event.key && event.key != 'Enter') {
      return;
    }
    this.loadData(1, this.page.size, this.getConditions());
  }

  loadData(pageNumber, pageSize, conditions) {
    this.logService
      .list(conditions, pageNumber, pageSize)
      .then(data => this.page = data as Page<Log>)
      .catch(reason => this.errorHandler(reason));
  }

  loadDataAsExcel() {
    this.logService
      .listAsExcel(this.getConditions())
      .then(blob => {
        saveAs(blob, this.translate.getParsedResult({}, "log.export.file.name") + ".xlsx");
      })
      .catch(reason => this.errorHandler(reason));
  }

  resetConditions() {
    this.conditions = {
      keywords: '',
      begin: undefined,
      end: undefined
    }
  }

  private errorHandler(error) {
    Commons.errorHandler(error, this.router, this.modalService);
  }

}
