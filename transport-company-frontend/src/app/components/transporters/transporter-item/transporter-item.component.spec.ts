import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransporterItemComponent } from './transporter-item.component';

describe('TransporterItemComponent', () => {
  let component: TransporterItemComponent;
  let fixture: ComponentFixture<TransporterItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransporterItemComponent]
    });
    fixture = TestBed.createComponent(TransporterItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
