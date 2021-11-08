jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FamilleService } from '../service/famille.service';
import { IFamille, Famille } from '../famille.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISituationFamiliale } from 'app/entities/situation-familiale/situation-familiale.model';
import { SituationFamilialeService } from 'app/entities/situation-familiale/service/situation-familiale.service';
import { IConjoint } from 'app/entities/conjoint/conjoint.model';
import { ConjointService } from 'app/entities/conjoint/service/conjoint.service';

import { FamilleUpdateComponent } from './famille-update.component';

describe('Famille Management Update Component', () => {
  let comp: FamilleUpdateComponent;
  let fixture: ComponentFixture<FamilleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let familleService: FamilleService;
  let employeService: EmployeService;
  let situationFamilialeService: SituationFamilialeService;
  let conjointService: ConjointService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FamilleUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FamilleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FamilleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    familleService = TestBed.inject(FamilleService);
    employeService = TestBed.inject(EmployeService);
    situationFamilialeService = TestBed.inject(SituationFamilialeService);
    conjointService = TestBed.inject(ConjointService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employe query and add missing value', () => {
      const famille: IFamille = { id: 456 };
      const employe: IEmploye = { id: 61452 };
      famille.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 97423 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SituationFamiliale query and add missing value', () => {
      const famille: IFamille = { id: 456 };
      const situationFamiliale: ISituationFamiliale = { id: 40129 };
      famille.situationFamiliale = situationFamiliale;

      const situationFamilialeCollection: ISituationFamiliale[] = [{ id: 84142 }];
      jest.spyOn(situationFamilialeService, 'query').mockReturnValue(of(new HttpResponse({ body: situationFamilialeCollection })));
      const additionalSituationFamiliales = [situationFamiliale];
      const expectedCollection: ISituationFamiliale[] = [...additionalSituationFamiliales, ...situationFamilialeCollection];
      jest.spyOn(situationFamilialeService, 'addSituationFamilialeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      expect(situationFamilialeService.query).toHaveBeenCalled();
      expect(situationFamilialeService.addSituationFamilialeToCollectionIfMissing).toHaveBeenCalledWith(
        situationFamilialeCollection,
        ...additionalSituationFamiliales
      );
      expect(comp.situationFamilialesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Conjoint query and add missing value', () => {
      const famille: IFamille = { id: 456 };
      const conjoint: IConjoint = { id: 67274 };
      famille.conjoint = conjoint;

      const conjointCollection: IConjoint[] = [{ id: 32649 }];
      jest.spyOn(conjointService, 'query').mockReturnValue(of(new HttpResponse({ body: conjointCollection })));
      const additionalConjoints = [conjoint];
      const expectedCollection: IConjoint[] = [...additionalConjoints, ...conjointCollection];
      jest.spyOn(conjointService, 'addConjointToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      expect(conjointService.query).toHaveBeenCalled();
      expect(conjointService.addConjointToCollectionIfMissing).toHaveBeenCalledWith(conjointCollection, ...additionalConjoints);
      expect(comp.conjointsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const famille: IFamille = { id: 456 };
      const employe: IEmploye = { id: 47513 };
      famille.employe = employe;
      const situationFamiliale: ISituationFamiliale = { id: 17297 };
      famille.situationFamiliale = situationFamiliale;
      const conjoint: IConjoint = { id: 44482 };
      famille.conjoint = conjoint;

      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(famille));
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.situationFamilialesSharedCollection).toContain(situationFamiliale);
      expect(comp.conjointsSharedCollection).toContain(conjoint);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = { id: 123 };
      jest.spyOn(familleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: famille }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(familleService.update).toHaveBeenCalledWith(famille);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = new Famille();
      jest.spyOn(familleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: famille }));
      saveSubject.complete();

      // THEN
      expect(familleService.create).toHaveBeenCalledWith(famille);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = { id: 123 };
      jest.spyOn(familleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(familleService.update).toHaveBeenCalledWith(famille);
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

    describe('trackSituationFamilialeById', () => {
      it('Should return tracked SituationFamiliale primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationFamilialeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackConjointById', () => {
      it('Should return tracked Conjoint primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackConjointById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
