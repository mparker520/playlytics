import { TestBed } from '@angular/core/testing';

import { OwnedGame } from './owned-game';

describe('OwnedGame', () => {
  let service: OwnedGame;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OwnedGame);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
