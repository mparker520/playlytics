import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-add-owned-game-component',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './add-owned-game-component.html',
  styleUrl: './add-owned-game-component.css'
})
export class AddOwnedGameComponent {

  //<editor-fold desc="Variables">

        expanded: boolean = false;
        clicked: boolean = false;

  //</editor-fold>

  //<editor-fold desc="Inputs and Outputs">
  @Input() games!: GameResponseDTO[];

        @Output() lookup = new EventEmitter<string>;
        triggerLookup(searchValue: string) {
          this.lookup.emit(searchValue);
          this.clicked = true;
        }

        @Output() add = new EventEmitter<number>;
        triggerAdd(gameId: number) {
          this.add.emit(gameId)
          this.clicked = false;
        }
  //</editor-fold>

  onExpandChange() {
    this.expanded = !this.expanded;
  }


}
