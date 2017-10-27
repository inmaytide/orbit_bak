import {Component, Input} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'layer-alert',
  template: `
    <div class="modal-body">
      <i class="fa fa-{{icon}} {{iconCss}}"></i>
      <div class="alter-message">{{message}}</div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-sm btn-success" (click)="submit()">{{'layer.confirm.btn.ok' | translate}}</button>
      <button type="button" class="btn btn-sm btn-secondary" (click)="close()">{{'layer.confirm.btn.cancel' | translate}}</button>
    </div>
  `,
  styleUrls: ['./layer.css']
})

export class LayerConfirm {
  @Input() message;

  @Input() icon = 'smile-o';

  @Input() iconCss = "green";

  constructor(public activeModal: NgbActiveModal) {
  }

  close() {
    this.activeModal.close(false);
  }

  submit() {
    this.activeModal.close(true);
  }
}
