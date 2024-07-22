import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAssessmentComponent } from './edit-assessment.component';

describe('EditAssessmentComponent', () => {
  let component: EditAssessmentComponent;
  let fixture: ComponentFixture<EditAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditAssessmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
