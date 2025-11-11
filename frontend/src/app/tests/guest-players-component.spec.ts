import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestPlayersComponent } from '../components/connections-component/guest-players-component/guest-players-component';

describe('GuestPlayersComponent', () => {
  let component: GuestPlayersComponent;
  let fixture: ComponentFixture<GuestPlayersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestPlayersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GuestPlayersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
