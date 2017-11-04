import {Component} from "@angular/core";
import {SidebarComponent} from "./nav/sidebar.component";

@Component({
  selector: 'main',
  templateUrl: './main.html'
})
export class MainComponent {
  public isCollapsed = false;
}
