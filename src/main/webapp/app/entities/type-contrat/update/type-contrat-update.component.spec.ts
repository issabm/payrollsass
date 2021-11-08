jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeContratService } from '../service/type-contrat.service';
import { ITypeContrat, TypeContrat } from '../type-contrat.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { TypeContratUpdateComponent } from './type-contrat-update.component';

describe('TypeContrat Management Update Component', () => {
  let comp: TypeContratUpdateComponent;
  let fixture: ComponentFixture<TypeContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeContratService: TypeContratService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TypeContratUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TypeContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeContratService = TestBed.inject(TypeContratService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const typeContrat: ITypeContrat = { id: 456 };
      const pays: IPays = { id: 16072 };
      typeContrat.pays = pays;

      const paysCollection: IPays[] = [{ id: 53800 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ typeContrat });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const typeContrat: ITypeContrat = { id: 456 };
      const pays: IPays = { id: 55528 };
      typeContrat.pays = pays;

      activatedRoute.data = of({ typeContrat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(typeContrat));
      expect(comp.paysSharedCollection).toContain(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeContrat>>();
      const typeContrat = { id: 123 };
      jest.spyOn(typeContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeContrat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeContratService.update).toHaveBeenCalledWith(typeContrat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeContrat>>();
      const typeContrat = new TypeContrat();
      jest.spyOn(typeContratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeContrat }));
      saveSubject.complete();

      // THEN
      expect(typeContratService.create).toHaveBeenCalledWith(typeContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeContrat>>();
      const typeContrat = { id: 123 };
      jest.spyOn(typeContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeContratService.update).toHaveBeenCalledWith(typeContrat);
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
