import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOwnedGameComponent } from '../components/inventory-component/add-owned-game-component/add-owned-game-component';

describe('AddOwnedGameComponent', () => {
  let component: AddOwnedGameComponent;
  let fixture: ComponentFixture<AddOwnedGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddOwnedGameComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddOwnedGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
