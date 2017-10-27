import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LayerAlert} from "./layers/layer.alert";
import {LayerConfirm} from "./layers/layer.confirm";

export class Commons {
  static errorHandler(error: any, router: Router, modalService: NgbModal) {
    switch (error.status) {
      case 403:
        router.navigateByUrl("error_403")
        break;
      case 401:
        router.navigateByUrl("login");
        break;
      case 409:
        Commons.error(modalService, JSON.parse(error.error).message);
        break;
      default:
        console.log(error);
    }
  }

  static alert(modalService: NgbModal, options: { message: string, icon: string, iconCss: string }) {
    const modalRef = modalService.open(LayerAlert, {size: 'sm', windowClass: 'modal-message'});
    Object.assign(modalRef.componentInstance, options);
  }

  static success(modalService: NgbModal, message: string) {
    Commons.alert(modalService, {
      message: message,
      icon: 'smile-o',
      iconCss: 'success'
    })
  }

  static error(modalService: NgbModal, message: string) {
    Commons.alert(modalService, {
      message: message,
      icon: 'frown-o',
      iconCss: 'danger'
    });
  }

  static confirm(modalService: NgbModal, message: string): Promise<any> {
    const modalRef = modalService.open(LayerConfirm, {size: 'sm', backdrop: 'static', windowClass: 'modal-message'});
    Object.assign(modalRef.componentInstance, {
      message: message,
      icon: 'question-circle-o',
      iconCss: 'warning'
    });
    return modalRef.result;
  }
}
