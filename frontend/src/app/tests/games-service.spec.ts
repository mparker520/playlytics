import { TestBed } from '@angular/core/testing';

import { BoardGamesService } from '../services/board-games-service';

describe('BoardGamesService', () => {
  let service: BoardGamesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoardGamesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
