import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {DatePipe, NgOptimizedImage} from "@angular/common";
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {GamePlaySessionResponseDTO} from '../../../dtos/game-play-session-response-dto';
import {GameResponseDTO} from '../../../dtos/game-response-dto';

@Component({
  selector: 'app-game-play-sessions-list-component',
  imports: [
    NgOptimizedImage,
    DatePipe
  ],
  templateUrl: './game-play-sessions-list-component.html',
  styleUrl: './game-play-sessions-list-component.css'
})
export class GamePlaySessionListComponent{

  expanded: boolean = false

  onExpandChange() {
    this.expanded = !this.expanded;
  }

  @Input() playSessions: GamePlaySessionResponseDTO[] = [];

  @Output() delete =  new EventEmitter<number>;

  triggerDelete(id: number) {
    this.delete.emit(id)
  }


}
