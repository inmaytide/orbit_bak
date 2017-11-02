import {Component, OnInit} from "@angular/core";
import {MPage} from "../../../m-controls/models/m-page-model";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Commons} from "../../../commons";
import {TranslateService} from "@ngx-translate/core";
import {User} from "../../../models/user-model";
import {UserService} from "./user.service";
import {MColumnModel} from "../../../m-controls/models/m-column-model";

@Component({
  selector: "user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.css"]
})
export class UserComponent implements OnInit {

  private keywords: string = "";
  private page: MPage<User> = new MPage<User>();
  private models: MColumnModel[] = [];
  private searchBarDisplay = false;




  constructor(private service: UserService,
              private router: Router,
              private modalService: NgbModal,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.models = [{
      text: "user.column.username",
      name: "username",
      event: this.lookup(this.service, this.modalService)
    }, {
      text: "user.column.name",
      name: "name"
    }, {
      text: "user.column.org",
      name: "organization"
    }, {
      text: "user.column.status",
      name: "status"
    }];
    this.loadData(1, 10);
  }

  public lookup(service, modalService) {
    return function (user) {
      console.log(service);
      console.log(modalService);
      console.log(user);
    }
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

  public remove() {
    let ids = ["12321", "123123"];
    this.service.remove(ids)
      .then(response => console.log(response))
      .catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

  public displaySearchBar() {
    this.searchBarDisplay = !this.searchBarDisplay;
  }


}
