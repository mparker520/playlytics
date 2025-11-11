import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmedConnectionsComponent } from '../components/connections-component/registered-players-component/confirmed-connections-component/confirmed-connections-component';

describe('ConfirmedConnectionsComponent', () => {
  let component: ConfirmedConnectionsComponent;
  let fixture: ComponentFixture<ConfirmedConnectionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmedConnectionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmedConnectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
