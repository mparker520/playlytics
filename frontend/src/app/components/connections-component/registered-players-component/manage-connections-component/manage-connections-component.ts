import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../../../dtos/registered-player-response-dto';

@Component({
  selector: 'app-manage-connections-component',
  imports: [],
  templateUrl: './manage-connections-component.html',
  styleUrl: './manage-connections-component.css'
})
export class ManageConnectionsComponent {

  //<editor-fold desc="Discover Players">
  @Input() registeredPlayer?: RegisteredPlayerResponseDTO;

  @Output() lookup = new EventEmitter<string>;
  triggerLookup(searchValue: string) {
    this.lookup.emit(searchValue);
  }

  @Output() send = new EventEmitter<number>;
  triggerSend(id: number) {
    this.send.emit(id)
  }

  @Output() block = new EventEmitter<number>;
  triggerBlock(id: number) {
    this.block.emit(id)
  }

  //</editor-fold>

}
