jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PalierConditionService } from '../service/palier-condition.service';
import { IPalierCondition, PalierCondition } from '../palier-condition.model';
import { IPalierPlate } from 'app/entities/palier-plate/palier-plate.model';
import { PalierPlateService } from 'app/entities/palier-plate/service/palier-plate.service';

import { PalierConditionUpdateComponent } from './palier-condition-update.component';

describe('PalierCondition Management Update Component', () => {
  let comp: PalierConditionUpdateComponent;
  let fixture: ComponentFixture<PalierConditionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let palierConditionService: PalierConditionService;
  let palierPlateService: PalierPlateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PalierConditionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PalierConditionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PalierConditionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    palierConditionService = TestBed.inject(PalierConditionService);
    palierPlateService = TestBed.inject(PalierPlateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PalierPlate query and add missing value', () => {
      const palierCondition: IPalierCondition = { id: 456 };
      const palierPlate: IPalierPlate = { id: 11506 };
      palierCondition.palierPlate = palierPlate;

      const palierPlateCollection: IPalierPlate[] = [{ id: 10918 }];
      jest.spyOn(palierPlateService, 'query').mockReturnValue(of(new HttpResponse({ body: palierPlateCollection })));
      const additionalPalierPlates = [palierPlate];
      const expectedCollection: IPalierPlate[] = [...additionalPalierPlates, ...palierPlateCollection];
      jest.spyOn(palierPlateService, 'addPalierPlateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierCondition });
      comp.ngOnInit();

      expect(palierPlateService.query).toHaveBeenCalled();
      expect(palierPlateService.addPalierPlateToCollectionIfMissing).toHaveBeenCalledWith(palierPlateCollection, ...additionalPalierPlates);
      expect(comp.palierPlatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const palierCondition: IPalierCondition = { id: 456 };
      const palierPlate: IPalierPlate = { id: 45334 };
      palierCondition.palierPlate = palierPlate;

      activatedRoute.data = of({ palierCondition });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(palierCondition));
      expect(comp.palierPlatesSharedCollection).toContain(palierPlate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierCondition>>();
      const palierCondition = { id: 123 };
      jest.spyOn(palierConditionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierCondition });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierCondition }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(palierConditionService.update).toHaveBeenCalledWith(palierCondition);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierCondition>>();
      const palierCondition = new PalierCondition();
      jest.spyOn(palierConditionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierCondition });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierCondition }));
      saveSubject.complete();

      // THEN
      expect(palierConditionService.create).toHaveBeenCalledWith(palierCondition);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierCondition>>();
      const palierCondition = { id: 123 };
      jest.spyOn(palierConditionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierCondition });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(palierConditionService.update).toHaveBeenCalledWith(palierCondition);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPalierPlateById', () => {
      it('Should return tracked PalierPlate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPalierPlateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
