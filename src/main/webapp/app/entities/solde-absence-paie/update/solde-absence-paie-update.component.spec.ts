jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldeAbsencePaieService } from '../service/solde-absence-paie.service';
import { ISoldeAbsencePaie, SoldeAbsencePaie } from '../solde-absence-paie.model';
import { IPayroll } from 'app/entities/payroll/payroll.model';
import { PayrollService } from 'app/entities/payroll/service/payroll.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';

import { SoldeAbsencePaieUpdateComponent } from './solde-absence-paie-update.component';

describe('SoldeAbsencePaie Management Update Component', () => {
  let comp: SoldeAbsencePaieUpdateComponent;
  let fixture: ComponentFixture<SoldeAbsencePaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldeAbsencePaieService: SoldeAbsencePaieService;
  let payrollService: PayrollService;
  let natureAbsenceService: NatureAbsenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeAbsencePaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldeAbsencePaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldeAbsencePaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldeAbsencePaieService = TestBed.inject(SoldeAbsencePaieService);
    payrollService = TestBed.inject(PayrollService);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Payroll query and add missing value', () => {
      const soldeAbsencePaie: ISoldeAbsencePaie = { id: 456 };
      const payroll: IPayroll = { id: 10498 };
      soldeAbsencePaie.payroll = payroll;

      const payrollCollection: IPayroll[] = [{ id: 3591 }];
      jest.spyOn(payrollService, 'query').mockReturnValue(of(new HttpResponse({ body: payrollCollection })));
      const additionalPayrolls = [payroll];
      const expectedCollection: IPayroll[] = [...additionalPayrolls, ...payrollCollection];
      jest.spyOn(payrollService, 'addPayrollToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      expect(payrollService.query).toHaveBeenCalled();
      expect(payrollService.addPayrollToCollectionIfMissing).toHaveBeenCalledWith(payrollCollection, ...additionalPayrolls);
      expect(comp.payrollsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureAbsence query and add missing value', () => {
      const soldeAbsencePaie: ISoldeAbsencePaie = { id: 456 };
      const natureAbsence: INatureAbsence = { id: 62082 };
      soldeAbsencePaie.natureAbsence = natureAbsence;

      const natureAbsenceCollection: INatureAbsence[] = [{ id: 18103 }];
      jest.spyOn(natureAbsenceService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAbsenceCollection })));
      const additionalNatureAbsences = [natureAbsence];
      const expectedCollection: INatureAbsence[] = [...additionalNatureAbsences, ...natureAbsenceCollection];
      jest.spyOn(natureAbsenceService, 'addNatureAbsenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      expect(natureAbsenceService.query).toHaveBeenCalled();
      expect(natureAbsenceService.addNatureAbsenceToCollectionIfMissing).toHaveBeenCalledWith(
        natureAbsenceCollection,
        ...additionalNatureAbsences
      );
      expect(comp.natureAbsencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldeAbsencePaie: ISoldeAbsencePaie = { id: 456 };
      const payroll: IPayroll = { id: 41996 };
      soldeAbsencePaie.payroll = payroll;
      const natureAbsence: INatureAbsence = { id: 60105 };
      soldeAbsencePaie.natureAbsence = natureAbsence;

      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldeAbsencePaie));
      expect(comp.payrollsSharedCollection).toContain(payroll);
      expect(comp.natureAbsencesSharedCollection).toContain(natureAbsence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsencePaie>>();
      const soldeAbsencePaie = { id: 123 };
      jest.spyOn(soldeAbsencePaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsencePaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldeAbsencePaieService.update).toHaveBeenCalledWith(soldeAbsencePaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsencePaie>>();
      const soldeAbsencePaie = new SoldeAbsencePaie();
      jest.spyOn(soldeAbsencePaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsencePaie }));
      saveSubject.complete();

      // THEN
      expect(soldeAbsencePaieService.create).toHaveBeenCalledWith(soldeAbsencePaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsencePaie>>();
      const soldeAbsencePaie = { id: 123 };
      jest.spyOn(soldeAbsencePaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsencePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldeAbsencePaieService.update).toHaveBeenCalledWith(soldeAbsencePaie);
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

    describe('trackNatureAbsenceById', () => {
      it('Should return tracked NatureAbsence primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureAbsenceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
