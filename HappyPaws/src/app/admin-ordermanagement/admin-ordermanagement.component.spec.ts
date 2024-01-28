import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOrdermanagementComponent } from './admin-ordermanagement.component';

describe('AdminOrdermanagementComponent', () => {
  let component: AdminOrdermanagementComponent;
  let fixture: ComponentFixture<AdminOrdermanagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminOrdermanagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOrdermanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
