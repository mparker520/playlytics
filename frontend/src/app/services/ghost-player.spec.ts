import { TestBed } from '@angular/core/testing';

import { GhostPlayer } from './ghost-player';

describe('GhostPlayer', () => {
  let service: GhostPlayer;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GhostPlayer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
