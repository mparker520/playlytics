import { TestBed } from '@angular/core/testing';

import { GamePlaySessionService } from './game-play-session-service';

describe('GamePlaySessionService', () => {
  let service: GamePlaySessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GamePlaySessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
