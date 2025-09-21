import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageConnectionsComponent } from '../components/connections-component/registered-players-component/manage-connections-component/manage-connections-component';

describe('ManageConnectionsComponent', () => {
  let component: ManageConnectionsComponent;
  let fixture: ComponentFixture<ManageConnectionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageConnectionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageConnectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
