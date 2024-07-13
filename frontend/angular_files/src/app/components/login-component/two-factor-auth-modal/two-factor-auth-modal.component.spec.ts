import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TwoFactorAuthModalComponent } from './two-factor-auth-modal.component';

describe('TwoFactorAuthModalComponent', () => {
  let component: TwoFactorAuthModalComponent;
  let fixture: ComponentFixture<TwoFactorAuthModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TwoFactorAuthModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TwoFactorAuthModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
