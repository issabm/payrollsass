jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SousTypeContratService } from '../service/sous-type-contrat.service';
import { ISousTypeContrat, SousTypeContrat } from '../sous-type-contrat.model';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

import { SousTypeContratUpdateComponent } from './sous-type-contrat-update.component';

describe('SousTypeContrat Management Update Component', () => {
  let comp: SousTypeContratUpdateComponent;
  let fixture: ComponentFixture<SousTypeContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sousTypeContratService: SousTypeContratService;
  let typeContratService: TypeContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SousTypeContratUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SousTypeContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SousTypeContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sousTypeContratService = TestBed.inject(SousTypeContratService);
    typeContratService = TestBed.inject(TypeContratService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeContrat query and add missing value', () => {
      const sousTypeContrat: ISousTypeContrat = { id: 456 };
      const typeContrat: ITypeContrat = { id: 6472 };
      sousTypeContrat.typeContrat = typeContrat;

      const typeContratCollection: ITypeContrat[] = [{ id: 82393 }];
      jest.spyOn(typeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: typeContratCollection })));
      const additionalTypeContrats = [typeContrat];
      const expectedCollection: ITypeContrat[] = [...additionalTypeContrats, ...typeContratCollection];
      jest.spyOn(typeContratService, 'addTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sousTypeContrat });
      comp.ngOnInit();

      expect(typeContratService.query).toHaveBeenCalled();
      expect(typeContratService.addTypeContratToCollectionIfMissing).toHaveBeenCalledWith(typeContratCollection, ...additionalTypeContrats);
      expect(comp.typeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sousTypeContrat: ISousTypeContrat = { id: 456 };
      const typeContrat: ITypeContrat = { id: 81020 };
      sousTypeContrat.typeContrat = typeContrat;

      activatedRoute.data = of({ sousTypeContrat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sousTypeContrat));
      expect(comp.typeContratsSharedCollection).toContain(typeContrat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SousTypeContrat>>();
      const sousTypeContrat = { id: 123 };
      jest.spyOn(sousTypeContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sousTypeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sousTypeContrat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sousTypeContratService.update).toHaveBeenCalledWith(sousTypeContrat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SousTypeContrat>>();
      const sousTypeContrat = new SousTypeContrat();
      jest.spyOn(sousTypeContratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sousTypeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sousTypeContrat }));
      saveSubject.complete();

      // THEN
      expect(sousTypeContratService.create).toHaveBeenCalledWith(sousTypeContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SousTypeContrat>>();
      const sousTypeContrat = { id: 123 };
      jest.spyOn(sousTypeContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sousTypeContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sousTypeContratService.update).toHaveBeenCalledWith(sousTypeContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTypeContratById', () => {
      it('Should return tracked TypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeContratById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
