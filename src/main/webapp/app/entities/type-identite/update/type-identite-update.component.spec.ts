jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeIdentiteService } from '../service/type-identite.service';
import { ITypeIdentite, TypeIdentite } from '../type-identite.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { TypeIdentiteUpdateComponent } from './type-identite-update.component';

describe('TypeIdentite Management Update Component', () => {
  let comp: TypeIdentiteUpdateComponent;
  let fixture: ComponentFixture<TypeIdentiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeIdentiteService: TypeIdentiteService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TypeIdentiteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TypeIdentiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeIdentiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeIdentiteService = TestBed.inject(TypeIdentiteService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const typeIdentite: ITypeIdentite = { id: 456 };
      const pays: IPays = { id: 94260 };
      typeIdentite.pays = pays;

      const paysCollection: IPays[] = [{ id: 30231 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const typeIdentite: ITypeIdentite = { id: 456 };
      const pays: IPays = { id: 37613 };
      typeIdentite.pays = pays;

      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(typeIdentite));
      expect(comp.paysSharedCollection).toContain(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = { id: 123 };
      jest.spyOn(typeIdentiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeIdentite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeIdentiteService.update).toHaveBeenCalledWith(typeIdentite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = new TypeIdentite();
      jest.spyOn(typeIdentiteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeIdentite }));
      saveSubject.complete();

      // THEN
      expect(typeIdentiteService.create).toHaveBeenCalledWith(typeIdentite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = { id: 123 };
      jest.spyOn(typeIdentiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeIdentiteService.update).toHaveBeenCalledWith(typeIdentite);
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
