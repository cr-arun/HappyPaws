import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProductmanagementComponent } from './admin-productmanagement.component';

describe('AdminProductmanagementComponent', () => {
  let component: AdminProductmanagementComponent;
  let fixture: ComponentFixture<AdminProductmanagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminProductmanagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminProductmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
