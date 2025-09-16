import { TestBed } from '@angular/core/testing';

import { GamePlaySession } from './game-play-session';

describe('GamePlaySessions', () => {
  let service: GamePlaySession;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GamePlaySession);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
