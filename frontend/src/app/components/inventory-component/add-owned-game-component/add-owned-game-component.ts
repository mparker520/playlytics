import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GameResponseDTO} from '../../../dtos/game-response-dto';

@Component({
  selector: 'app-add-owned-game-component',
  imports: [],
  templateUrl: './add-owned-game-component.html',
  styleUrl: './add-owned-game-component.css'
})
export class AddOwnedGameComponent {
        @Input() games!: GameResponseDTO[];

        @Output() lookup = new EventEmitter<string>;
        triggerLookup(searchValue: string) {
          this.lookup.emit(searchValue);
        }

        @Output() add = new EventEmitter<number>;
        triggerAdd(gameId: number) {
          this.add.emit(gameId)
        }

}
