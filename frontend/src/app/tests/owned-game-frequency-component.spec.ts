import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnedGameFrequencyComponent } from '../components/analytics-component/owned-game-frequency-component/owned-game-frequency-component';

describe('OwnedGameFrequencyComponent', () => {
  let component: OwnedGameFrequencyComponent;
  let fixture: ComponentFixture<OwnedGameFrequencyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OwnedGameFrequencyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnedGameFrequencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
