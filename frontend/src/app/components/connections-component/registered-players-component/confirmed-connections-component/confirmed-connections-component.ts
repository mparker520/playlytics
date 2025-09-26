import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../../../dtos/registered-player-response-dto';
import {NgOptimizedImage} from '@angular/common';
import {GhostPlayerResponseDTO} from '../../../../dtos/ghost-player-response-dto';




@Component({
  selector: 'app-confirmed-connections-component',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './confirmed-connections-component.html',
  styleUrl: './confirmed-connections-component.css'
})
export class ConfirmedConnectionsComponent {

  expandedConnectionsList: boolean = false;

  @Input() connections: RegisteredPlayerResponseDTO[] = [];

  filteredConnections?: RegisteredPlayerResponseDTO[];

  ngOnChanges(changes: SimpleChanges) {
    if(changes['connections'] && this.connections) {
      this.filteredConnections = this.connections;
    }
  }



  @Output() remove = new EventEmitter<number>
    triggerRemove(id: number) {
      this.remove.emit(id);
    }

    @Output() block = new EventEmitter<number>
    triggerBlock(id: number) {
      this.block.emit(id);
    }

  onExpandConnectionsListChange() {
      this.expandedConnectionsList = !this.expandedConnectionsList;
  }

  triggerConnectionLookup(searchConnectionBox: string) {
    this.filteredConnections = this.connections.filter(connections => {
      console.log(connections.displayName + " " + searchConnectionBox);
        const fullName = (connections.firstName + " " + connections.lastName).toLowerCase();
        return connections.loginEmail.toLowerCase().includes(searchConnectionBox.toLowerCase()) ||
          fullName.toLowerCase().includes(searchConnectionBox.toLowerCase()) || connections.displayName.toLowerCase().includes(searchConnectionBox.toLowerCase());
      }
    );
  }

  triggerFilterClear() {
    this.filteredConnections = this.connections;
  }



}
