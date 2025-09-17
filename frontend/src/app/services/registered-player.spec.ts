import { TestBed } from '@angular/core/testing';

import { RegisteredPlayerService } from './registered-player-service';

describe('RegisteredPlayer', () => {
  let service: RegisteredPlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisteredPlayerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
