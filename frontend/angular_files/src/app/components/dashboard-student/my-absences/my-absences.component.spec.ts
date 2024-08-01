import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAbsencesComponent } from './my-absences.component';

describe('MyAbsencesComponent', () => {
  let component: MyAbsencesComponent;
  let fixture: ComponentFixture<MyAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyAbsencesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
