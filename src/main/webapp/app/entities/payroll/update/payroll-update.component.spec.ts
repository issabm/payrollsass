jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PayrollService } from '../service/payroll.service';
import { IPayroll, Payroll } from '../payroll.model';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';
import { IMouvementPaie } from 'app/entities/mouvement-paie/mouvement-paie.model';
import { MouvementPaieService } from 'app/entities/mouvement-paie/service/mouvement-paie.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

import { PayrollUpdateComponent } from './payroll-update.component';

describe('Payroll Management Update Component', () => {
  let comp: PayrollUpdateComponent;
  let fixture: ComponentFixture<PayrollUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let payrollService: PayrollService;
  let deviseService: DeviseService;
  let mouvementPaieService: MouvementPaieService;
  let situationService: SituationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PayrollUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PayrollUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayrollUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    payrollService = TestBed.inject(PayrollService);
    deviseService = TestBed.inject(DeviseService);
    mouvementPaieService = TestBed.inject(MouvementPaieService);
    situationService = TestBed.inject(SituationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Devise query and add missing value', () => {
      const payroll: IPayroll = { id: 456 };
      const devise: IDevise = { id: 24443 };
      payroll.devise = devise;

      const deviseCollection: IDevise[] = [{ id: 54584 }];
      jest.spyOn(deviseService, 'query').mockReturnValue(of(new HttpResponse({ body: deviseCollection })));
      const additionalDevises = [devise];
      const expectedCollection: IDevise[] = [...additionalDevises, ...deviseCollection];
      jest.spyOn(deviseService, 'addDeviseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      expect(deviseService.query).toHaveBeenCalled();
      expect(deviseService.addDeviseToCollectionIfMissing).toHaveBeenCalledWith(deviseCollection, ...additionalDevises);
      expect(comp.devisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MouvementPaie query and add missing value', () => {
      const payroll: IPayroll = { id: 456 };
      const mvt: IMouvementPaie = { id: 62671 };
      payroll.mvt = mvt;

      const mouvementPaieCollection: IMouvementPaie[] = [{ id: 10496 }];
      jest.spyOn(mouvementPaieService, 'query').mockReturnValue(of(new HttpResponse({ body: mouvementPaieCollection })));
      const additionalMouvementPaies = [mvt];
      const expectedCollection: IMouvementPaie[] = [...additionalMouvementPaies, ...mouvementPaieCollection];
      jest.spyOn(mouvementPaieService, 'addMouvementPaieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      expect(mouvementPaieService.query).toHaveBeenCalled();
      expect(mouvementPaieService.addMouvementPaieToCollectionIfMissing).toHaveBeenCalledWith(
        mouvementPaieCollection,
        ...additionalMouvementPaies
      );
      expect(comp.mouvementPaiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Situation query and add missing value', () => {
      const payroll: IPayroll = { id: 456 };
      const situation: ISituation = { id: 70696 };
      payroll.situation = situation;

      const situationCollection: ISituation[] = [{ id: 21993 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const payroll: IPayroll = { id: 456 };
      const devise: IDevise = { id: 71011 };
      payroll.devise = devise;
      const mvt: IMouvementPaie = { id: 36377 };
      payroll.mvt = mvt;
      const situation: ISituation = { id: 43608 };
      payroll.situation = situation;

      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payroll));
      expect(comp.devisesSharedCollection).toContain(devise);
      expect(comp.mouvementPaiesSharedCollection).toContain(mvt);
      expect(comp.situationsSharedCollection).toContain(situation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payroll>>();
      const payroll = { id: 123 };
      jest.spyOn(payrollService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payroll }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(payrollService.update).toHaveBeenCalledWith(payroll);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payroll>>();
      const payroll = new Payroll();
      jest.spyOn(payrollService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payroll }));
      saveSubject.complete();

      // THEN
      expect(payrollService.create).toHaveBeenCalledWith(payroll);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payroll>>();
      const payroll = { id: 123 };
      jest.spyOn(payrollService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payroll });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(payrollService.update).toHaveBeenCalledWith(payroll);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDeviseById', () => {
      it('Should return tracked Devise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMouvementPaieById', () => {
      it('Should return tracked MouvementPaie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMouvementPaieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
