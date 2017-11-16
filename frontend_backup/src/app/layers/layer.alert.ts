import {Component, Input} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'layer-alert',
  template: `
    <div class="modal-body">
      <i class="fa fa-{{icon}} {{iconCss}}"></i>
      <div class="alter-message">{{message}}</div>
    </div>
  `,
  styleUrls: ['./layer.css']
})

export class LayerAlert {
  @Input() message;

  @Input() icon = 'smile-o';

  @Input() iconCss = "green";

  constructor(public activeModal: NgbActiveModal) {
    
  }
}
