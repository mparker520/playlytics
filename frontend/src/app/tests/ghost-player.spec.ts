import { TestBed } from '@angular/core/testing';

import { GhostPlayerService } from '../services/ghost-player-service';

describe('GhostPlayer', () => {
  let service: GhostPlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GhostPlayerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
