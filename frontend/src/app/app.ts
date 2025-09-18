import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ShellComponent} from './components/shell-component/shell-component';


@Component({
  selector: 'app-root',
  imports: [ShellComponent, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}
