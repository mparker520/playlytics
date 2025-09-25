import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {GamePlaySessionResponseDTO} from '../../../dtos/game-play-session-response-dto';

@Component({
  selector: 'app-game-play-sessions-list-component',
    imports: [
        NgOptimizedImage
    ],
  templateUrl: './game-play-sessions-list-component.html',
  styleUrl: './game-play-sessions-list-component.css'
})
export class GamePlaySessionListComponent {

  @Input() playSessions!: GamePlaySessionResponseDTO[];

  @Output() delete =  new EventEmitter<number>;

  triggerDelete(id: number) {
    this.delete.emit(id)
  }


}
