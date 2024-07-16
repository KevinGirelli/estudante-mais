import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeachersFromClassComponent } from './teachers-from-class.component';

describe('TeachersFromClassComponent', () => {
  let component: TeachersFromClassComponent;
  let fixture: ComponentFixture<TeachersFromClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeachersFromClassComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeachersFromClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
