import { Component } from '@angular/core';
import {WinLossComponent} from './win-loss-component/win-loss-component';
import {GameResponseDTO} from '../../dtos/GameResponseDTO';



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

  playedGames: GameResponseDTO[] = [
    {id: 1, name: 'Canvas'},
    {id: 2, name: 'Lost Cities'},
    {id: 3, name: 'Wingspan'},
    {id: 4, name: 'Azul, Queen Garden'}
  ];



}
