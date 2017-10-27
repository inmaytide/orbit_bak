import {Component, OnInit} from "@angular/core";
import {Page} from "../../../models/page-model";
import {Role} from "../../../models/role-model";
import {RoleService} from "./role.service";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Commons} from "../../../commons";
import {RoleModalComponent} from "./role-modal.component";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: "role",
  templateUrl: "./role.component.html",
  styleUrls: ["./role.css"]
})
export class RoleComponent implements OnInit {

  public keywords: string = "";

  public page: Page<Role> = new Page<Role>();

  public allChecked = "";

  constructor(private service: RoleService,
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

  public add() {
    let modalRef = this.modalService.open(RoleModalComponent, {size: 'lg', backdrop: 'static'});
    modalRef.result.then(result => this.loadData(this.page.number + 1, this.page.size));
  }

  public remove() {
    let ids = [];
    this.page.content.forEach(role => {
      if (role['state'] == 'selected') {
        ids.push(role.id)
      }
    });

    if (ids.length == 0) {
      Commons.error(this.modalService, this.translate.getParsedResult({}, "role.remove.nochosen"));
    } else {
      Commons.confirm(this.modalService, this.translate.getParsedResult({}, "role.remove.confirm"))
        .then(result => {
          if (result) {
            this.service.remove(ids)
              .then(response => this.loadData(1, this.page.size))
              .catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
          }
        });
    }
  }

  public selectRole(role) {
    if (role.id === "9999") {
      return;
    }
    role.state = role.state == 'selected' ? 'none' : 'selected';
    if (role.state === 'none') {
      this.allChecked = "";
    }
  }

  public selectAll() {
    this.page.content.forEach(role => {
      if (role.id !== "9999") {
        role['state'] = this.allChecked === 'checked' ? 'none' : 'selected';
      }
    });
    this.allChecked = this.allChecked === 'checked' ? '' : 'checked';
  }

  public edit(id: string) {
    this.service.get(id)
      .then(role => {
        let modalRef = this.modalService.open(RoleModalComponent, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.role = role;
        modalRef.componentInstance.state = "lock";
        modalRef.result.then(result => this.loadData(this.page.number + 1, this.page.size));
      }).catch(reason => Commons.errorHandler(reason, this.router, this.modalService));
  }

}
