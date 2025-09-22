import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GamePlaySessionsListComponent } from '../components/sessions-component/game-play-sessions-list-component/game-play-sessions-list-component';

describe('GamePlaySessionsListComponent', () => {
  let component: GamePlaySessionsListComponent;
  let fixture: ComponentFixture<GamePlaySessionsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GamePlaySessionsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GamePlaySessionsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
