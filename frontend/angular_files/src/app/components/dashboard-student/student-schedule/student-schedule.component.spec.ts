import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentScheduleComponent } from './student-schedule.component';

describe('StudentScheduleComponent', () => {
  let component: StudentScheduleComponent;
  let fixture: ComponentFixture<StudentScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentScheduleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
