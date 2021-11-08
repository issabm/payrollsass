jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ManagementResourceFunService } from '../service/management-resource-fun.service';
import { IManagementResourceFun, ManagementResourceFun } from '../management-resource-fun.model';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';
import { ManagementResourceService } from 'app/entities/management-resource/service/management-resource.service';

import { ManagementResourceFunUpdateComponent } from './management-resource-fun-update.component';

describe('ManagementResourceFun Management Update Component', () => {
  let comp: ManagementResourceFunUpdateComponent;
  let fixture: ComponentFixture<ManagementResourceFunUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let managementResourceFunService: ManagementResourceFunService;
  let managementResourceService: ManagementResourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ManagementResourceFunUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ManagementResourceFunUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ManagementResourceFunUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    managementResourceFunService = TestBed.inject(ManagementResourceFunService);
    managementResourceService = TestBed.inject(ManagementResourceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ManagementResource query and add missing value', () => {
      const managementResourceFun: IManagementResourceFun = { id: 456 };
      const ressourceManage: IManagementResource = { id: 47406 };
      managementResourceFun.ressourceManage = ressourceManage;

      const managementResourceCollection: IManagementResource[] = [{ id: 11874 }];
      jest.spyOn(managementResourceService, 'query').mockReturnValue(of(new HttpResponse({ body: managementResourceCollection })));
      const additionalManagementResources = [ressourceManage];
      const expectedCollection: IManagementResource[] = [...additionalManagementResources, ...managementResourceCollection];
      jest.spyOn(managementResourceService, 'addManagementResourceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResourceFun });
      comp.ngOnInit();

      expect(managementResourceService.query).toHaveBeenCalled();
      expect(managementResourceService.addManagementResourceToCollectionIfMissing).toHaveBeenCalledWith(
        managementResourceCollection,
        ...additionalManagementResources
      );
      expect(comp.managementResourcesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const managementResourceFun: IManagementResourceFun = { id: 456 };
      const ressourceManage: IManagementResource = { id: 8086 };
      managementResourceFun.ressourceManage = ressourceManage;

      activatedRoute.data = of({ managementResourceFun });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(managementResourceFun));
      expect(comp.managementResourcesSharedCollection).toContain(ressourceManage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceFun>>();
      const managementResourceFun = { id: 123 };
      jest.spyOn(managementResourceFunService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceFun });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResourceFun }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(managementResourceFunService.update).toHaveBeenCalledWith(managementResourceFun);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceFun>>();
      const managementResourceFun = new ManagementResourceFun();
      jest.spyOn(managementResourceFunService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceFun });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResourceFun }));
      saveSubject.complete();

      // THEN
      expect(managementResourceFunService.create).toHaveBeenCalledWith(managementResourceFun);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceFun>>();
      const managementResourceFun = { id: 123 };
      jest.spyOn(managementResourceFunService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceFun });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(managementResourceFunService.update).toHaveBeenCalledWith(managementResourceFun);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackManagementResourceById', () => {
      it('Should return tracked ManagementResource primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackManagementResourceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
