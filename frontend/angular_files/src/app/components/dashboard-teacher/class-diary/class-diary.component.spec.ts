import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassDiaryComponent } from './class-diary.component';

describe('ClassDiaryComponent', () => {
  let component: ClassDiaryComponent;
  let fixture: ComponentFixture<ClassDiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassDiaryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClassDiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
