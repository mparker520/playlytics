import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayTrendsComponent } from '../components/analytics-component/play-trends-component/play-trends-component';

describe('PlayTrendsComponent', () => {
  let component: PlayTrendsComponent;
  let fixture: ComponentFixture<PlayTrendsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayTrendsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayTrendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
