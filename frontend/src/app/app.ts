import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Shell} from './components/shell/shell';

@Component({
  selector: 'app-root',
  imports: [Shell, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}
