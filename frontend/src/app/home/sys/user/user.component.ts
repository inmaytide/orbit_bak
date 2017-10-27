import {Component, OnInit} from "@angular/core";
import {Page} from "../../../models/page-model";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Commons} from "../../../commons";
import {TranslateService} from "@ngx-translate/core";
import {User} from "../../../models/user-model";
import {UserService} from "./user.service";

@Component({
  selector: "user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.css"]
})
export class UserComponent implements OnInit {

  public keywords: string = "";

  public page: Page<User> = new Page<User>();

  public allChecked = "";

  constructor(private service: UserService,
              private router: Router,
              private modalService: NgbModal,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.loadData(1, 10);
  }

  public pageChange(pageable) {
    this.loadData(pageable.number, pageable.size);
  }

  private loadData(number: number, size: number) {
    this.service.list({keywords: this.keywords, number, size})
      .subscribe(response => this.page = response,
        error => Commons.errorHandler(error, this.router, this.modalService));
  }

  public search(event) {
    if (event.key && event.key != 'Enter') {
      return;
    }
    this.loadData(1, this.page.size);
  }


}
