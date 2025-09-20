import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../../../dtos/registered-player-response-dto';




@Component({
  selector: 'app-confirmed-connections-component',
  imports: [],
  templateUrl: './confirmed-connections-component.html',
  styleUrl: './confirmed-connections-component.css'
})
export class ConfirmedConnectionsComponent {

    @Input() connections?: RegisteredPlayerResponseDTO[]

    @Output() remove = new EventEmitter<number>
    triggerRemove(id: number) {
      this.remove.emit(id);
    }

    @Output() block = new EventEmitter<number>
    triggerBlock(id: number) {
      this.block.emit(id);
    }

}
