jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { IdentiteService } from '../service/identite.service';
import { IIdentite, Identite } from '../identite.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ITypeIdentite } from 'app/entities/type-identite/type-identite.model';
import { TypeIdentiteService } from 'app/entities/type-identite/service/type-identite.service';

import { IdentiteUpdateComponent } from './identite-update.component';

describe('Identite Management Update Component', () => {
  let comp: IdentiteUpdateComponent;
  let fixture: ComponentFixture<IdentiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let identiteService: IdentiteService;
  let employeService: EmployeService;
  let typeIdentiteService: TypeIdentiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [IdentiteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(IdentiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IdentiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    identiteService = TestBed.inject(IdentiteService);
    employeService = TestBed.inject(EmployeService);
    typeIdentiteService = TestBed.inject(TypeIdentiteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employe query and add missing value', () => {
      const identite: IIdentite = { id: 456 };
      const employe: IEmploye = { id: 63022 };
      identite.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 71036 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeIdentite query and add missing value', () => {
      const identite: IIdentite = { id: 456 };
      const typeIdentite: ITypeIdentite = { id: 31283 };
      identite.typeIdentite = typeIdentite;

      const typeIdentiteCollection: ITypeIdentite[] = [{ id: 69002 }];
      jest.spyOn(typeIdentiteService, 'query').mockReturnValue(of(new HttpResponse({ body: typeIdentiteCollection })));
      const additionalTypeIdentites = [typeIdentite];
      const expectedCollection: ITypeIdentite[] = [...additionalTypeIdentites, ...typeIdentiteCollection];
      jest.spyOn(typeIdentiteService, 'addTypeIdentiteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      expect(typeIdentiteService.query).toHaveBeenCalled();
      expect(typeIdentiteService.addTypeIdentiteToCollectionIfMissing).toHaveBeenCalledWith(
        typeIdentiteCollection,
        ...additionalTypeIdentites
      );
      expect(comp.typeIdentitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const identite: IIdentite = { id: 456 };
      const employe: IEmploye = { id: 58279 };
      identite.employe = employe;
      const typeIdentite: ITypeIdentite = { id: 61115 };
      identite.typeIdentite = typeIdentite;

      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(identite));
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.typeIdentitesSharedCollection).toContain(typeIdentite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Identite>>();
      const identite = { id: 123 };
      jest.spyOn(identiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: identite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(identiteService.update).toHaveBeenCalledWith(identite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Identite>>();
      const identite = new Identite();
      jest.spyOn(identiteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: identite }));
      saveSubject.complete();

      // THEN
      expect(identiteService.create).toHaveBeenCalledWith(identite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Identite>>();
      const identite = { id: 123 };
      jest.spyOn(identiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ identite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(identiteService.update).toHaveBeenCalledWith(identite);
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

    describe('trackTypeIdentiteById', () => {
      it('Should return tracked TypeIdentite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeIdentiteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
