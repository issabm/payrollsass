jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NiveauEtudeService } from '../service/niveau-etude.service';
import { INiveauEtude, NiveauEtude } from '../niveau-etude.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { NiveauEtudeUpdateComponent } from './niveau-etude-update.component';

describe('NiveauEtude Management Update Component', () => {
  let comp: NiveauEtudeUpdateComponent;
  let fixture: ComponentFixture<NiveauEtudeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let niveauEtudeService: NiveauEtudeService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NiveauEtudeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NiveauEtudeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NiveauEtudeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    niveauEtudeService = TestBed.inject(NiveauEtudeService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const niveauEtude: INiveauEtude = { id: 456 };
      const pays: IPays = { id: 13533 };
      niveauEtude.pays = pays;

      const paysCollection: IPays[] = [{ id: 76041 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ niveauEtude });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const niveauEtude: INiveauEtude = { id: 456 };
      const pays: IPays = { id: 23650 };
      niveauEtude.pays = pays;

      activatedRoute.data = of({ niveauEtude });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(niveauEtude));
      expect(comp.paysSharedCollection).toContain(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauEtude>>();
      const niveauEtude = { id: 123 };
      jest.spyOn(niveauEtudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauEtude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauEtude }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(niveauEtudeService.update).toHaveBeenCalledWith(niveauEtude);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauEtude>>();
      const niveauEtude = new NiveauEtude();
      jest.spyOn(niveauEtudeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauEtude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauEtude }));
      saveSubject.complete();

      // THEN
      expect(niveauEtudeService.create).toHaveBeenCalledWith(niveauEtude);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauEtude>>();
      const niveauEtude = { id: 123 };
      jest.spyOn(niveauEtudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauEtude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(niveauEtudeService.update).toHaveBeenCalledWith(niveauEtude);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
