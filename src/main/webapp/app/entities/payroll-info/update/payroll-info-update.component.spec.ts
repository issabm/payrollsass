jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PayrollInfoService } from '../service/payroll-info.service';
import { IPayrollInfo, PayrollInfo } from '../payroll-info.model';
import { IPayroll } from 'app/entities/payroll/payroll.model';
import { PayrollService } from 'app/entities/payroll/service/payroll.service';

import { PayrollInfoUpdateComponent } from './payroll-info-update.component';

describe('PayrollInfo Management Update Component', () => {
  let comp: PayrollInfoUpdateComponent;
  let fixture: ComponentFixture<PayrollInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let payrollInfoService: PayrollInfoService;
  let payrollService: PayrollService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PayrollInfoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PayrollInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayrollInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    payrollInfoService = TestBed.inject(PayrollInfoService);
    payrollService = TestBed.inject(PayrollService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Payroll query and add missing value', () => {
      const payrollInfo: IPayrollInfo = { id: 456 };
      const payroll: IPayroll = { id: 30142 };
      payrollInfo.payroll = payroll;

      const payrollCollection: IPayroll[] = [{ id: 59144 }];
      jest.spyOn(payrollService, 'query').mockReturnValue(of(new HttpResponse({ body: payrollCollection })));
      const additionalPayrolls = [payroll];
      const expectedCollection: IPayroll[] = [...additionalPayrolls, ...payrollCollection];
      jest.spyOn(payrollService, 'addPayrollToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payrollInfo });
      comp.ngOnInit();

      expect(payrollService.query).toHaveBeenCalled();
      expect(payrollService.addPayrollToCollectionIfMissing).toHaveBeenCalledWith(payrollCollection, ...additionalPayrolls);
      expect(comp.payrollsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const payrollInfo: IPayrollInfo = { id: 456 };
      const payroll: IPayroll = { id: 54178 };
      payrollInfo.payroll = payroll;

      activatedRoute.data = of({ payrollInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payrollInfo));
      expect(comp.payrollsSharedCollection).toContain(payroll);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayrollInfo>>();
      const payrollInfo = { id: 123 };
      jest.spyOn(payrollInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payrollInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payrollInfo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(payrollInfoService.update).toHaveBeenCalledWith(payrollInfo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayrollInfo>>();
      const payrollInfo = new PayrollInfo();
      jest.spyOn(payrollInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payrollInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payrollInfo }));
      saveSubject.complete();

      // THEN
      expect(payrollInfoService.create).toHaveBeenCalledWith(payrollInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayrollInfo>>();
      const payrollInfo = { id: 123 };
      jest.spyOn(payrollInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payrollInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(payrollInfoService.update).toHaveBeenCalledWith(payrollInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPayrollById', () => {
      it('Should return tracked Payroll primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPayrollById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
