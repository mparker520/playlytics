import {Component, OnInit, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AuthService} from './services/auth-service';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit{

  constructor(private authService: AuthService) {
  }


  ngOnInit() {
    this.authService.loadCurrentUser().subscribe({
      next: () => {},
      error: () => console.log("Not logged in")
    });
  }

}
