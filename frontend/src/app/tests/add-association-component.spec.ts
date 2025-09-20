import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAssociationComponent } from '../components/connections-component/guest-players-component/add-association-component/add-association-component';

describe('AddAssociationComponent', () => {
  let component: AddAssociationComponent;
  let fixture: ComponentFixture<AddAssociationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAssociationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAssociationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
