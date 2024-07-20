import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAssessmentsComponent } from './my-assessments.component';

describe('MyAssessmentsComponent', () => {
  let component: MyAssessmentsComponent;
  let fixture: ComponentFixture<MyAssessmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyAssessmentsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyAssessmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
