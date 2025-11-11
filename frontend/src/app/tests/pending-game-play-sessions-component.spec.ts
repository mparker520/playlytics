import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingGamePlaySessionsComponent } from '../components/sessions-component/pending-game-play-sessions-component/pending-game-play-sessions-component';

describe('PendingGamePlaySessionsComponent', () => {
  let component: PendingGamePlaySessionsComponent;
  let fixture: ComponentFixture<PendingGamePlaySessionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingGamePlaySessionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingGamePlaySessionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
