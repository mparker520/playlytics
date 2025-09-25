import {Component, OnInit} from '@angular/core';
import {
  AddGamePlaySessionComponent
} from '../sessions-component/add-game-play-session-component/add-game-play-session-component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgOptimizedImage} from '@angular/common';
import {RouterLink} from '@angular/router';
import {GamePlaySessionService} from '../../services/game-play-session-service';

@Component({
  selector: 'app-home-component',
  imports: [
    AddGamePlaySessionComponent,
    FormsModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css'
})
export class HomeComponent{


}
