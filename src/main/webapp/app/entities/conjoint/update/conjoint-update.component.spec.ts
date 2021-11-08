jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ConjointService } from '../service/conjoint.service';
import { IConjoint, Conjoint } from '../conjoint.model';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { ConjointUpdateComponent } from './conjoint-update.component';

describe('Conjoint Management Update Component', () => {
  let comp: ConjointUpdateComponent;
  let fixture: ComponentFixture<ConjointUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let conjointService: ConjointService;
  let sexeService: SexeService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ConjointUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ConjointUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConjointUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    conjointService = TestBed.inject(ConjointService);
    sexeService = TestBed.inject(SexeService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sexe query and add missing value', () => {
      const conjoint: IConjoint = { id: 456 };
      const sexe: ISexe = { id: 29120 };
      conjoint.sexe = sexe;

      const sexeCollection: ISexe[] = [{ id: 12260 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexes = [sexe];
      const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const conjoint: IConjoint = { id: 456 };
      const nationalite: IPays = { id: 33594 };
      conjoint.nationalite = nationalite;

      const paysCollection: IPays[] = [{ id: 47093 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [nationalite];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const conjoint: IConjoint = { id: 456 };
      const sexe: ISexe = { id: 56330 };
      conjoint.sexe = sexe;
      const nationalite: IPays = { id: 39040 };
      conjoint.nationalite = nationalite;

      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(conjoint));
      expect(comp.sexesSharedCollection).toContain(sexe);
      expect(comp.paysSharedCollection).toContain(nationalite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conjoint>>();
      const conjoint = { id: 123 };
      jest.spyOn(conjointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: conjoint }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(conjointService.update).toHaveBeenCalledWith(conjoint);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conjoint>>();
      const conjoint = new Conjoint();
      jest.spyOn(conjointService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: conjoint }));
      saveSubject.complete();

      // THEN
      expect(conjointService.create).toHaveBeenCalledWith(conjoint);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conjoint>>();
      const conjoint = { id: 123 };
      jest.spyOn(conjointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conjoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(conjointService.update).toHaveBeenCalledWith(conjoint);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSexeById', () => {
      it('Should return tracked Sexe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSexeById(0, entity);
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
  });
});
