import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAssessmentComponent } from './list-assessment.component';

describe('ListAssessmentComponent', () => {
  let component: ListAssessmentComponent;
  let fixture: ComponentFixture<ListAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAssessmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
