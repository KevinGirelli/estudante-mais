import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherEditComponent } from './teacher-edit.component';

describe('TeacherEditComponent', () => {
  let component: TeacherEditComponent;
  let fixture: ComponentFixture<TeacherEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeacherEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
