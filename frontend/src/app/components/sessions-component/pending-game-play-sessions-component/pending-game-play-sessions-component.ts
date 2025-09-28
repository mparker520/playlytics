import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GamePlaySessionResponseDTO} from '../../../dtos/game-play-session-response-dto';
import {DatePipe, NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-pending-game-play-sessions-component',
  imports: [
    DatePipe,
    NgOptimizedImage
  ],
  templateUrl: './pending-game-play-sessions-component.html',
  styleUrl: './pending-game-play-sessions-component.css'
})
export class PendingGamePlaySessionsComponent {

  expanded: boolean = false

  onPendingExpandChange() {
    this.expanded = !this.expanded;
  }

  @Input() pendingPlaySessions: GamePlaySessionResponseDTO[] = [];


  @Output() accept =  new EventEmitter<number>;


  triggerAccept(id: number) {
    this.accept.emit(id)
  }


}
