import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGamePlaySessionComponent } from '../components/sessions-component/add-game-play-session-component/add-game-play-session-component';

describe('AddGamePlaySessionComponent', () => {
  let component: AddGamePlaySessionComponent;
  let fixture: ComponentFixture<AddGamePlaySessionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGamePlaySessionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGamePlaySessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
