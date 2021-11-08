jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldeAbsenceService } from '../service/solde-absence.service';
import { ISoldeAbsence, SoldeAbsence } from '../solde-absence.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';

import { SoldeAbsenceUpdateComponent } from './solde-absence-update.component';

describe('SoldeAbsence Management Update Component', () => {
  let comp: SoldeAbsenceUpdateComponent;
  let fixture: ComponentFixture<SoldeAbsenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldeAbsenceService: SoldeAbsenceService;
  let employeService: EmployeService;
  let natureAbsenceService: NatureAbsenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeAbsenceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldeAbsenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldeAbsenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldeAbsenceService = TestBed.inject(SoldeAbsenceService);
    employeService = TestBed.inject(EmployeService);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employe query and add missing value', () => {
      const soldeAbsence: ISoldeAbsence = { id: 456 };
      const employe: IEmploye = { id: 59996 };
      soldeAbsence.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 25947 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureAbsence query and add missing value', () => {
      const soldeAbsence: ISoldeAbsence = { id: 456 };
      const natureAbsence: INatureAbsence = { id: 10347 };
      soldeAbsence.natureAbsence = natureAbsence;

      const natureAbsenceCollection: INatureAbsence[] = [{ id: 37088 }];
      jest.spyOn(natureAbsenceService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAbsenceCollection })));
      const additionalNatureAbsences = [natureAbsence];
      const expectedCollection: INatureAbsence[] = [...additionalNatureAbsences, ...natureAbsenceCollection];
      jest.spyOn(natureAbsenceService, 'addNatureAbsenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      expect(natureAbsenceService.query).toHaveBeenCalled();
      expect(natureAbsenceService.addNatureAbsenceToCollectionIfMissing).toHaveBeenCalledWith(
        natureAbsenceCollection,
        ...additionalNatureAbsences
      );
      expect(comp.natureAbsencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldeAbsence: ISoldeAbsence = { id: 456 };
      const employe: IEmploye = { id: 22110 };
      soldeAbsence.employe = employe;
      const natureAbsence: INatureAbsence = { id: 57515 };
      soldeAbsence.natureAbsence = natureAbsence;

      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldeAbsence));
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.natureAbsencesSharedCollection).toContain(natureAbsence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsence>>();
      const soldeAbsence = { id: 123 };
      jest.spyOn(soldeAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsence }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldeAbsenceService.update).toHaveBeenCalledWith(soldeAbsence);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsence>>();
      const soldeAbsence = new SoldeAbsence();
      jest.spyOn(soldeAbsenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsence }));
      saveSubject.complete();

      // THEN
      expect(soldeAbsenceService.create).toHaveBeenCalledWith(soldeAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsence>>();
      const soldeAbsence = { id: 123 };
      jest.spyOn(soldeAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldeAbsenceService.update).toHaveBeenCalledWith(soldeAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEmployeById', () => {
      it('Should return tracked Employe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeById(0, entity);
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
