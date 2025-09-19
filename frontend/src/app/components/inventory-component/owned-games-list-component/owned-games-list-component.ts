import {Component, EventEmitter, Input, Output} from '@angular/core';
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';

@Component({
  selector: 'app-owned-games-list-component',
  imports: [],
  templateUrl: './owned-games-list-component.html',
  styleUrl: './owned-games-list-component.css'
})
export class OwnedGamesListComponent {
  @Input() ownedGames!: OwnedGameResponseDTO[];
  @Output() delete =  new EventEmitter<number>;

  triggerDelete(id: number) {
    this.delete.emit(id)
  }

}
