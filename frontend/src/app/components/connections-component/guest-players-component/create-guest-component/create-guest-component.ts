import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {GhostPlayerDTO} from '../../../../dtos/ghost-player-dto';

@Component({
  selector: 'app-create-guest-component',
  imports: [
    FormsModule
  ],
  templateUrl: './create-guest-component.html',
  styleUrl: './create-guest-component.css'
})
export class CreateGuestComponent {

  firstName: string = '';
  lastName: string = '';
  identifierEmail: string = '';
  @Input() createGhostErrorMessage?: string;


  @Output() create = new EventEmitter<GhostPlayerDTO>

  triggerCreate(form: NgForm) {

    const ghostPlayerDTO: GhostPlayerDTO = {
      firstName: this.firstName,
      lastName: this.lastName,
      identifierEmail: this.identifierEmail
    };

      this.create.emit(ghostPlayerDTO)
      form.resetForm();


  }

}


