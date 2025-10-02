import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../../../dtos/registered-player-response-dto';
import {ConnectionRequestResponseDTO} from '../../../../dtos/connection-request-response-dto';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-manage-connections-component',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './manage-connections-component.html',
  styleUrl: './manage-connections-component.css'
})
export class ManageConnectionsComponent {

  expandedConnectionsManagement: boolean = false;

  //<editor-fold desc="Discover Players / Send Requests / Block">
  @Input() registeredPlayer?: RegisteredPlayerResponseDTO;
  @Input() errorMessage?: string;

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

  onExpandConnectionsManagementChange() {
    this.expandedConnectionsManagement = !this.expandedConnectionsManagement;
  }

  //</editor-fold>

  //<editor-fold desc="Sent Connection Requests">

  @Input() sentRequests?: ConnectionRequestResponseDTO[];

  @Output() cancel = new EventEmitter<number>
  triggerCancel(id: number) {
    this.cancel.emit(id);
  }

  //</editor-fold>

  //<editor-fold desc="Pending Connection Requests">

  @Input() pendingRequests?: ConnectionRequestResponseDTO[];

  @Output() reject = new EventEmitter<number>
  triggerReject(id: number) {
    this.reject.emit(id);
  }

  @Output() accept = new EventEmitter<number>
  triggerAccept(id: number) {
    this.accept.emit(id);
  }

  //</editor-fold>

  //<editor-fold desc="Blocked Players">
  @Input() blockedPlayers?: RegisteredPlayerResponseDTO[];

  @Output() unblock = new EventEmitter<number>
  triggerUnblock(id: number) {
      this.unblock.emit(id);
  }

  //</editor-fold>

}
