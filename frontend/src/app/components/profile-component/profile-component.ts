import { Component } from '@angular/core';
import {AddOwnedGameComponent} from '../inventory-component/add-owned-game-component/add-owned-game-component';
import {OwnedGamesListComponent} from '../inventory-component/owned-games-list-component/owned-games-list-component';
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-profile-component',
    imports: [
        AddOwnedGameComponent,
        OwnedGamesListComponent,
        NgOptimizedImage
    ],
  templateUrl: './profile-component.html',
  styleUrl: './profile-component.css'
})
export class ProfileComponent {

}
