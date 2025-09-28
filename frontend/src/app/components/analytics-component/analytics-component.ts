import { Component } from '@angular/core';
import {WinLossComponent} from './win-loss-component/win-loss-component';



@Component({
  selector: 'app-analytics-component',
  standalone: true,
  imports: [
    WinLossComponent
  ],
  templateUrl: './analytics-component.html',
  styleUrl: './analytics-component.css'
})
export class AnalyticsComponent {

}
