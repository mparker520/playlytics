import { TestBed } from '@angular/core/testing';

import { OwnedGameService } from './owned-game-service';

describe('OwnedGame', () => {
  let service: OwnedGameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OwnedGameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
