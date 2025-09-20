import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssociationsListComponent } from '../components/connections-component/guest-players-component/associations-list-component/associations-list-component';

describe('AssociationsListComponent', () => {
  let component: AssociationsListComponent;
  let fixture: ComponentFixture<AssociationsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssociationsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssociationsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
