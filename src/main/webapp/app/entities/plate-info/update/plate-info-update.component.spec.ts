jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PlateInfoService } from '../service/plate-info.service';
import { IPlateInfo, PlateInfo } from '../plate-info.model';
import { IPlate } from 'app/entities/plate/plate.model';
import { PlateService } from 'app/entities/plate/service/plate.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';

import { PlateInfoUpdateComponent } from './plate-info-update.component';

describe('PlateInfo Management Update Component', () => {
  let comp: PlateInfoUpdateComponent;
  let fixture: ComponentFixture<PlateInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plateInfoService: PlateInfoService;
  let plateService: PlateService;
  let rebriqueService: RebriqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlateInfoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PlateInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlateInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plateInfoService = TestBed.inject(PlateInfoService);
    plateService = TestBed.inject(PlateService);
    rebriqueService = TestBed.inject(RebriqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Plate query and add missing value', () => {
      const plateInfo: IPlateInfo = { id: 456 };
      const plate: IPlate = { id: 3048 };
      plateInfo.plate = plate;

      const plateCollection: IPlate[] = [{ id: 45644 }];
      jest.spyOn(plateService, 'query').mockReturnValue(of(new HttpResponse({ body: plateCollection })));
      const additionalPlates = [plate];
      const expectedCollection: IPlate[] = [...additionalPlates, ...plateCollection];
      jest.spyOn(plateService, 'addPlateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      expect(plateService.query).toHaveBeenCalled();
      expect(plateService.addPlateToCollectionIfMissing).toHaveBeenCalledWith(plateCollection, ...additionalPlates);
      expect(comp.platesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Rebrique query and add missing value', () => {
      const plateInfo: IPlateInfo = { id: 456 };
      const rebrique: IRebrique = { id: 89359 };
      plateInfo.rebrique = rebrique;

      const rebriqueCollection: IRebrique[] = [{ id: 96449 }];
      jest.spyOn(rebriqueService, 'query').mockReturnValue(of(new HttpResponse({ body: rebriqueCollection })));
      const additionalRebriques = [rebrique];
      const expectedCollection: IRebrique[] = [...additionalRebriques, ...rebriqueCollection];
      jest.spyOn(rebriqueService, 'addRebriqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      expect(rebriqueService.query).toHaveBeenCalled();
      expect(rebriqueService.addRebriqueToCollectionIfMissing).toHaveBeenCalledWith(rebriqueCollection, ...additionalRebriques);
      expect(comp.rebriquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plateInfo: IPlateInfo = { id: 456 };
      const plate: IPlate = { id: 68459 };
      plateInfo.plate = plate;
      const rebrique: IRebrique = { id: 14598 };
      plateInfo.rebrique = rebrique;

      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(plateInfo));
      expect(comp.platesSharedCollection).toContain(plate);
      expect(comp.rebriquesSharedCollection).toContain(rebrique);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlateInfo>>();
      const plateInfo = { id: 123 };
      jest.spyOn(plateInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plateInfo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(plateInfoService.update).toHaveBeenCalledWith(plateInfo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlateInfo>>();
      const plateInfo = new PlateInfo();
      jest.spyOn(plateInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plateInfo }));
      saveSubject.complete();

      // THEN
      expect(plateInfoService.create).toHaveBeenCalledWith(plateInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlateInfo>>();
      const plateInfo = { id: 123 };
      jest.spyOn(plateInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plateInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plateInfoService.update).toHaveBeenCalledWith(plateInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPlateById', () => {
      it('Should return tracked Plate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRebriqueById', () => {
      it('Should return tracked Rebrique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRebriqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
