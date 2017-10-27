import {Component, Injectable, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.css']
})

@Injectable()
export class HomeComponent implements OnInit {
  public menuStyle: string = "md";

  public menuClassObject: Object = {
    'nav-sm': false,
    'nav-md': true
  };

  constructor(public router: Router) {

  }

  changeMenuStyle() {
    this.menuStyle = this.menuStyle == "sm" ? "md" : "sm";
    this.menuClassObject = {
      'nav-sm': this.menuStyle == "sm",
      'nav-md': this.menuStyle == "md"
    }
  }

  ngOnInit(): void {
    //this.router.navigateByUrl("content");
  }


}
