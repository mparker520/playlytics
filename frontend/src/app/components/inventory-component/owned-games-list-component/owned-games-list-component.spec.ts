import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnedGamesListComponent } from './owned-games-list-component';

describe('OwnedGamesListComponent', () => {
  let component: OwnedGamesListComponent;
  let fixture: ComponentFixture<OwnedGamesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OwnedGamesListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnedGamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
