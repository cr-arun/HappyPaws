import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCategorymanagementComponent } from './admin-categorymanagement.component';

describe('AdminCategorymanagementComponent', () => {
  let component: AdminCategorymanagementComponent;
  let fixture: ComponentFixture<AdminCategorymanagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCategorymanagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCategorymanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
