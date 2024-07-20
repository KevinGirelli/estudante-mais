import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAssessmentComponent } from './create-assessment.component';

describe('CreateAssessmentComponent', () => {
  let component: CreateAssessmentComponent;
  let fixture: ComponentFixture<CreateAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateAssessmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
