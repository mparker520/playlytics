import { TestBed } from '@angular/core/testing';

import { RegisteredPlayer } from './registered-player';

describe('RegisteredPlayer', () => {
  let service: RegisteredPlayer;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisteredPlayer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
