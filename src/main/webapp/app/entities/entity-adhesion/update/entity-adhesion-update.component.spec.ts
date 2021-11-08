jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EntityAdhesionService } from '../service/entity-adhesion.service';
import { IEntityAdhesion, EntityAdhesion } from '../entity-adhesion.model';
import { INatureAdhesion } from 'app/entities/nature-adhesion/nature-adhesion.model';
import { NatureAdhesionService } from 'app/entities/nature-adhesion/service/nature-adhesion.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

import { EntityAdhesionUpdateComponent } from './entity-adhesion-update.component';

describe('EntityAdhesion Management Update Component', () => {
  let comp: EntityAdhesionUpdateComponent;
  let fixture: ComponentFixture<EntityAdhesionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entityAdhesionService: EntityAdhesionService;
  let natureAdhesionService: NatureAdhesionService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EntityAdhesionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EntityAdhesionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntityAdhesionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entityAdhesionService = TestBed.inject(EntityAdhesionService);
    natureAdhesionService = TestBed.inject(NatureAdhesionService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NatureAdhesion query and add missing value', () => {
      const entityAdhesion: IEntityAdhesion = { id: 456 };
      const natureAdhesion: INatureAdhesion = { id: 53840 };
      entityAdhesion.natureAdhesion = natureAdhesion;

      const natureAdhesionCollection: INatureAdhesion[] = [{ id: 70332 }];
      jest.spyOn(natureAdhesionService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAdhesionCollection })));
      const additionalNatureAdhesions = [natureAdhesion];
      const expectedCollection: INatureAdhesion[] = [...additionalNatureAdhesions, ...natureAdhesionCollection];
      jest.spyOn(natureAdhesionService, 'addNatureAdhesionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      expect(natureAdhesionService.query).toHaveBeenCalled();
      expect(natureAdhesionService.addNatureAdhesionToCollectionIfMissing).toHaveBeenCalledWith(
        natureAdhesionCollection,
        ...additionalNatureAdhesions
      );
      expect(comp.natureAdhesionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const entityAdhesion: IEntityAdhesion = { id: 456 };
      const pays: IPays = { id: 21149 };
      entityAdhesion.pays = pays;

      const paysCollection: IPays[] = [{ id: 25657 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const entityAdhesion: IEntityAdhesion = { id: 456 };
      const natureAdhesion: INatureAdhesion = { id: 46437 };
      entityAdhesion.natureAdhesion = natureAdhesion;
      const pays: IPays = { id: 73088 };
      entityAdhesion.pays = pays;

      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(entityAdhesion));
      expect(comp.natureAdhesionsSharedCollection).toContain(natureAdhesion);
      expect(comp.paysSharedCollection).toContain(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntityAdhesion>>();
      const entityAdhesion = { id: 123 };
      jest.spyOn(entityAdhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entityAdhesion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(entityAdhesionService.update).toHaveBeenCalledWith(entityAdhesion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntityAdhesion>>();
      const entityAdhesion = new EntityAdhesion();
      jest.spyOn(entityAdhesionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entityAdhesion }));
      saveSubject.complete();

      // THEN
      expect(entityAdhesionService.create).toHaveBeenCalledWith(entityAdhesion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntityAdhesion>>();
      const entityAdhesion = { id: 123 };
      jest.spyOn(entityAdhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entityAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entityAdhesionService.update).toHaveBeenCalledWith(entityAdhesion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackNatureAdhesionById', () => {
      it('Should return tracked NatureAdhesion primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureAdhesionById(0, entity);
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
