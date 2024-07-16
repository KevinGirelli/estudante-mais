import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentsFromClassComponent } from './students-from-class.component';

describe('StudentsFromClassComponent', () => {
  let component: StudentsFromClassComponent;
  let fixture: ComponentFixture<StudentsFromClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentsFromClassComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentsFromClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
