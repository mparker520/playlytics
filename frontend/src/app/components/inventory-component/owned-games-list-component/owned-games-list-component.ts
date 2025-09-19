import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-owned-games-list-component',
  imports: [],
  templateUrl: './owned-games-list-component.html',
  styleUrl: './owned-games-list-component.css'
})
export class OwnedGamesListComponent {
  @Input() ownedGames!: ({ id: number; title: string })[];


}
