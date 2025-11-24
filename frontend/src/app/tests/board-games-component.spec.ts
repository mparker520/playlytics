import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardGamesComponent } from '../components/board-games-component/board-games-component';

describe('BoardGamesComponent', () => {
  let component: BoardGamesComponent;
  let fixture: ComponentFixture<BoardGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoardGamesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
