import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchoolYearConfigComponent } from './school-year-config.component';

describe('SchoolYearConfigComponent', () => {
  let component: SchoolYearConfigComponent;
  let fixture: ComponentFixture<SchoolYearConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SchoolYearConfigComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SchoolYearConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
