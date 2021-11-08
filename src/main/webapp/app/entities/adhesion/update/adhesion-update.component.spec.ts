jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AdhesionService } from '../service/adhesion.service';
import { IAdhesion, Adhesion } from '../adhesion.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { IEntityAdhesion } from 'app/entities/entity-adhesion/entity-adhesion.model';
import { EntityAdhesionService } from 'app/entities/entity-adhesion/service/entity-adhesion.service';

import { AdhesionUpdateComponent } from './adhesion-update.component';

describe('Adhesion Management Update Component', () => {
  let comp: AdhesionUpdateComponent;
  let fixture: ComponentFixture<AdhesionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adhesionService: AdhesionService;
  let employeService: EmployeService;
  let entityAdhesionService: EntityAdhesionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AdhesionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AdhesionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdhesionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adhesionService = TestBed.inject(AdhesionService);
    employeService = TestBed.inject(EmployeService);
    entityAdhesionService = TestBed.inject(EntityAdhesionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employe query and add missing value', () => {
      const adhesion: IAdhesion = { id: 456 };
      const employe: IEmploye = { id: 95740 };
      adhesion.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 86211 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EntityAdhesion query and add missing value', () => {
      const adhesion: IAdhesion = { id: 456 };
      const entityAdhesion: IEntityAdhesion = { id: 7177 };
      adhesion.entityAdhesion = entityAdhesion;

      const entityAdhesionCollection: IEntityAdhesion[] = [{ id: 62530 }];
      jest.spyOn(entityAdhesionService, 'query').mockReturnValue(of(new HttpResponse({ body: entityAdhesionCollection })));
      const additionalEntityAdhesions = [entityAdhesion];
      const expectedCollection: IEntityAdhesion[] = [...additionalEntityAdhesions, ...entityAdhesionCollection];
      jest.spyOn(entityAdhesionService, 'addEntityAdhesionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      expect(entityAdhesionService.query).toHaveBeenCalled();
      expect(entityAdhesionService.addEntityAdhesionToCollectionIfMissing).toHaveBeenCalledWith(
        entityAdhesionCollection,
        ...additionalEntityAdhesions
      );
      expect(comp.entityAdhesionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adhesion: IAdhesion = { id: 456 };
      const employe: IEmploye = { id: 85963 };
      adhesion.employe = employe;
      const entityAdhesion: IEntityAdhesion = { id: 50649 };
      adhesion.entityAdhesion = entityAdhesion;

      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adhesion));
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.entityAdhesionsSharedCollection).toContain(entityAdhesion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Adhesion>>();
      const adhesion = { id: 123 };
      jest.spyOn(adhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adhesion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adhesionService.update).toHaveBeenCalledWith(adhesion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Adhesion>>();
      const adhesion = new Adhesion();
      jest.spyOn(adhesionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adhesion }));
      saveSubject.complete();

      // THEN
      expect(adhesionService.create).toHaveBeenCalledWith(adhesion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Adhesion>>();
      const adhesion = { id: 123 };
      jest.spyOn(adhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adhesionService.update).toHaveBeenCalledWith(adhesion);
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

    describe('trackEntityAdhesionById', () => {
      it('Should return tracked EntityAdhesion primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntityAdhesionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
