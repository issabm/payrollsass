jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeService } from '../service/employe.service';
import { IEmploye, Employe } from '../employe.model';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { TypeHandicapService } from 'app/entities/type-handicap/service/type-handicap.service';

import { EmployeUpdateComponent } from './employe-update.component';

describe('Employe Management Update Component', () => {
  let comp: EmployeUpdateComponent;
  let fixture: ComponentFixture<EmployeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeService: EmployeService;
  let situationService: SituationService;
  let paysService: PaysService;
  let typeHandicapService: TypeHandicapService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EmployeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EmployeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeService = TestBed.inject(EmployeService);
    situationService = TestBed.inject(SituationService);
    paysService = TestBed.inject(PaysService);
    typeHandicapService = TestBed.inject(TypeHandicapService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Situation query and add missing value', () => {
      const employe: IEmploye = { id: 456 };
      const situation: ISituation = { id: 60825 };
      employe.situation = situation;

      const situationCollection: ISituation[] = [{ id: 47209 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const employe: IEmploye = { id: 456 };
      const nationalite: IPays = { id: 76792 };
      employe.nationalite = nationalite;

      const paysCollection: IPays[] = [{ id: 50055 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [nationalite];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeHandicap query and add missing value', () => {
      const employe: IEmploye = { id: 456 };
      const typeHandicap: ITypeHandicap = { id: 3481 };
      employe.typeHandicap = typeHandicap;

      const typeHandicapCollection: ITypeHandicap[] = [{ id: 38327 }];
      jest.spyOn(typeHandicapService, 'query').mockReturnValue(of(new HttpResponse({ body: typeHandicapCollection })));
      const additionalTypeHandicaps = [typeHandicap];
      const expectedCollection: ITypeHandicap[] = [...additionalTypeHandicaps, ...typeHandicapCollection];
      jest.spyOn(typeHandicapService, 'addTypeHandicapToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(typeHandicapService.query).toHaveBeenCalled();
      expect(typeHandicapService.addTypeHandicapToCollectionIfMissing).toHaveBeenCalledWith(
        typeHandicapCollection,
        ...additionalTypeHandicaps
      );
      expect(comp.typeHandicapsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employe: IEmploye = { id: 456 };
      const situation: ISituation = { id: 51456 };
      employe.situation = situation;
      const nationalite: IPays = { id: 24554 };
      employe.nationalite = nationalite;
      const typeHandicap: ITypeHandicap = { id: 24083 };
      employe.typeHandicap = typeHandicap;

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(employe));
      expect(comp.situationsSharedCollection).toContain(situation);
      expect(comp.paysSharedCollection).toContain(nationalite);
      expect(comp.typeHandicapsSharedCollection).toContain(typeHandicap);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = { id: 123 };
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeService.update).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = new Employe();
      jest.spyOn(employeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(employeService.create).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = { id: 123 };
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeService.update).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTypeHandicapById', () => {
      it('Should return tracked TypeHandicap primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeHandicapById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
