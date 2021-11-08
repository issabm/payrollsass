import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollInfoDetailComponent } from './payroll-info-detail.component';

describe('PayrollInfo Management Detail Component', () => {
  let comp: PayrollInfoDetailComponent;
  let fixture: ComponentFixture<PayrollInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayrollInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ payrollInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PayrollInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PayrollInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load payrollInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.payrollInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
