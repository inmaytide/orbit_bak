import {Component, Input} from "@angular/core";
import {MTreeNode} from "./m-tree-node";

@Component({
  selector: "m-tree",
  template: `
    <ul class="tree-view" [ngStyle]="{display: !parent || parent['spread'] == true ? 'block' : 'none'}">
      <li *ngFor="let node of nodes">
        <div class="form-check">
          <i (click)="changeSpread(node)"
             [ngStyle]="{visibility: node.children.length > 0 ? 'none': 'hidden'}"
             class="fa fa-caret-{{node.spread ? 'down' : 'right'}}" aria-hidden="true"></i>
          <label class="form-check-label">
            <input checked="{{node.selected == true ? 'checked' : ''}}" class="form-check-input" (click)="selectNode($event, node)" type="checkbox"/>{{node.name}}
          </label>
        </div>
        <m-tree *ngIf="node.children.length > 0" [nodes]="node.children" [parent]="node"></m-tree>
      </li>
    </ul>
  `,
  styleUrls: ['./m-tree.css']
})
export class MTreeComponent {
  @Input()
  public nodes: MTreeNode[] = [];

  @Input()
  public parent;

  public changeSpread(node) {
    node.spread = !node.spread;
  }

  public selectNode(event, node) {
    node.selected = !node.selected;
  }
}
