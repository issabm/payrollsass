jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NiveauScolaireService } from '../service/niveau-scolaire.service';
import { INiveauScolaire, NiveauScolaire } from '../niveau-scolaire.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { NiveauScolaireUpdateComponent } from './niveau-scolaire-update.component';

describe('NiveauScolaire Management Update Component', () => {
  let comp: NiveauScolaireUpdateComponent;
  let fixture: ComponentFixture<NiveauScolaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let niveauScolaireService: NiveauScolaireService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NiveauScolaireUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NiveauScolaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NiveauScolaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    niveauScolaireService = TestBed.inject(NiveauScolaireService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const niveauScolaire: INiveauScolaire = { id: 456 };
      const pays: IPays = { id: 94983 };
      niveauScolaire.pays = pays;

      const paysCollection: IPays[] = [{ id: 8829 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const niveauScolaire: INiveauScolaire = { id: 456 };
      const pays: IPays = { id: 7252 };
      niveauScolaire.pays = pays;

      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(niveauScolaire));
      expect(comp.paysSharedCollection).toContain(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = { id: 123 };
      jest.spyOn(niveauScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauScolaire }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(niveauScolaireService.update).toHaveBeenCalledWith(niveauScolaire);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = new NiveauScolaire();
      jest.spyOn(niveauScolaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauScolaire }));
      saveSubject.complete();

      // THEN
      expect(niveauScolaireService.create).toHaveBeenCalledWith(niveauScolaire);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = { id: 123 };
      jest.spyOn(niveauScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(niveauScolaireService.update).toHaveBeenCalledWith(niveauScolaire);
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
