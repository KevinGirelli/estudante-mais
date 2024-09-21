import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditClassComponent } from './edit-class.component';

describe('EditClassComponent', () => {
  let component: EditClassComponent;
  let fixture: ComponentFixture<EditClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditClassComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
