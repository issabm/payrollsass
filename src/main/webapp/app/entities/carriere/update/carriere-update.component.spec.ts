jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CarriereService } from '../service/carriere.service';
import { ICarriere, Carriere } from '../carriere.model';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { EchlonService } from 'app/entities/echlon/service/echlon.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { EmploiService } from 'app/entities/emploi/service/emploi.service';
import { IAffectation } from 'app/entities/affectation/affectation.model';
import { AffectationService } from 'app/entities/affectation/service/affectation.service';

import { CarriereUpdateComponent } from './carriere-update.component';

describe('Carriere Management Update Component', () => {
  let comp: CarriereUpdateComponent;
  let fixture: ComponentFixture<CarriereUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carriereService: CarriereService;
  let echlonService: EchlonService;
  let categoryService: CategoryService;
  let emploiService: EmploiService;
  let affectationService: AffectationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CarriereUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(CarriereUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarriereUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carriereService = TestBed.inject(CarriereService);
    echlonService = TestBed.inject(EchlonService);
    categoryService = TestBed.inject(CategoryService);
    emploiService = TestBed.inject(EmploiService);
    affectationService = TestBed.inject(AffectationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Echlon query and add missing value', () => {
      const carriere: ICarriere = { id: 456 };
      const echlon: IEchlon = { id: 10965 };
      carriere.echlon = echlon;

      const echlonCollection: IEchlon[] = [{ id: 95142 }];
      jest.spyOn(echlonService, 'query').mockReturnValue(of(new HttpResponse({ body: echlonCollection })));
      const additionalEchlons = [echlon];
      const expectedCollection: IEchlon[] = [...additionalEchlons, ...echlonCollection];
      jest.spyOn(echlonService, 'addEchlonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      expect(echlonService.query).toHaveBeenCalled();
      expect(echlonService.addEchlonToCollectionIfMissing).toHaveBeenCalledWith(echlonCollection, ...additionalEchlons);
      expect(comp.echlonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const carriere: ICarriere = { id: 456 };
      const category: ICategory = { id: 55627 };
      carriere.category = category;

      const categoryCollection: ICategory[] = [{ id: 78462 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Emploi query and add missing value', () => {
      const carriere: ICarriere = { id: 456 };
      const emploi: IEmploi = { id: 47990 };
      carriere.emploi = emploi;

      const emploiCollection: IEmploi[] = [{ id: 19138 }];
      jest.spyOn(emploiService, 'query').mockReturnValue(of(new HttpResponse({ body: emploiCollection })));
      const additionalEmplois = [emploi];
      const expectedCollection: IEmploi[] = [...additionalEmplois, ...emploiCollection];
      jest.spyOn(emploiService, 'addEmploiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      expect(emploiService.query).toHaveBeenCalled();
      expect(emploiService.addEmploiToCollectionIfMissing).toHaveBeenCalledWith(emploiCollection, ...additionalEmplois);
      expect(comp.emploisSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affectation query and add missing value', () => {
      const carriere: ICarriere = { id: 456 };
      const affectation: IAffectation = { id: 15370 };
      carriere.affectation = affectation;

      const affectationCollection: IAffectation[] = [{ id: 77298 }];
      jest.spyOn(affectationService, 'query').mockReturnValue(of(new HttpResponse({ body: affectationCollection })));
      const additionalAffectations = [affectation];
      const expectedCollection: IAffectation[] = [...additionalAffectations, ...affectationCollection];
      jest.spyOn(affectationService, 'addAffectationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      expect(affectationService.query).toHaveBeenCalled();
      expect(affectationService.addAffectationToCollectionIfMissing).toHaveBeenCalledWith(affectationCollection, ...additionalAffectations);
      expect(comp.affectationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const carriere: ICarriere = { id: 456 };
      const echlon: IEchlon = { id: 93899 };
      carriere.echlon = echlon;
      const category: ICategory = { id: 29224 };
      carriere.category = category;
      const emploi: IEmploi = { id: 48524 };
      carriere.emploi = emploi;
      const affectation: IAffectation = { id: 29048 };
      carriere.affectation = affectation;

      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(carriere));
      expect(comp.echlonsSharedCollection).toContain(echlon);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.emploisSharedCollection).toContain(emploi);
      expect(comp.affectationsSharedCollection).toContain(affectation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carriere>>();
      const carriere = { id: 123 };
      jest.spyOn(carriereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carriere }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(carriereService.update).toHaveBeenCalledWith(carriere);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carriere>>();
      const carriere = new Carriere();
      jest.spyOn(carriereService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carriere }));
      saveSubject.complete();

      // THEN
      expect(carriereService.create).toHaveBeenCalledWith(carriere);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carriere>>();
      const carriere = { id: 123 };
      jest.spyOn(carriereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carriere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carriereService.update).toHaveBeenCalledWith(carriere);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEchlonById', () => {
      it('Should return tracked Echlon primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEchlonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCategoryById', () => {
      it('Should return tracked Category primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmploiById', () => {
      it('Should return tracked Emploi primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmploiById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAffectationById', () => {
      it('Should return tracked Affectation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffectationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
