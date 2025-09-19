import {Component, Input} from '@angular/core';
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';

@Component({
  selector: 'app-owned-games-list-component',
  imports: [],
  templateUrl: './owned-games-list-component.html',
  styleUrl: './owned-games-list-component.css'
})
export class OwnedGamesListComponent {
  @Input() ownedGames!: OwnedGameResponseDTO[];


}
