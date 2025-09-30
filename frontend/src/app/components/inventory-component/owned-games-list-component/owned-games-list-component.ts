import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-owned-games-list-component',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './owned-games-list-component.html',
  styleUrl: './owned-games-list-component.css'
})
export class OwnedGamesListComponent{



  @Input() ownedGames: OwnedGameResponseDTO[] = [];
  expanded: boolean = false;
  filteredOwnedGames?: OwnedGameResponseDTO[];

  ngOnChanges(changes: SimpleChanges) {
    if(changes['ownedGames'] && this.ownedGames) {
      this.filteredOwnedGames = this.ownedGames;
    }
  }

  @Output() delete =  new EventEmitter<number>;

  triggerDelete(id: number) {
    this.delete.emit(id)
  }

  triggerInventoryLookup(searchInventoryBox: string) {

  this.filteredOwnedGames = this.ownedGames.filter(ownedGame =>
    ownedGame.gameName.toLowerCase().includes(searchInventoryBox.toLowerCase())
  );
}


  onExpandChange() {
    this.expanded = !this.expanded;
  }

}
