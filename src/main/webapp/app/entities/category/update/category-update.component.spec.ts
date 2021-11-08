jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategoryService } from '../service/category.service';
import { ICategory, Category } from '../category.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { NatureConfigService } from 'app/entities/nature-config/service/nature-config.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';

import { CategoryUpdateComponent } from './category-update.component';

describe('Category Management Update Component', () => {
  let comp: CategoryUpdateComponent;
  let fixture: ComponentFixture<CategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoryService: CategoryService;
  let paysService: PaysService;
  let natureConfigService: NatureConfigService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CategoryUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(CategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoryService = TestBed.inject(CategoryService);
    paysService = TestBed.inject(PaysService);
    natureConfigService = TestBed.inject(NatureConfigService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const category: ICategory = { id: 456 };
      const pays: IPays = { id: 19725 };
      category.pays = pays;

      const paysCollection: IPays[] = [{ id: 35930 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureConfig query and add missing value', () => {
      const category: ICategory = { id: 456 };
      const natureConfig: INatureConfig = { id: 36317 };
      category.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 89294 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const category: ICategory = { id: 456 };
      const affilication: IAffiliation = { id: 73009 };
      category.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 16547 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const category: ICategory = { id: 456 };
      const entreprise: IEntreprise = { id: 13349 };
      category.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 80391 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const category: ICategory = { id: 456 };
      const groupe: IGroupe = { id: 25802 };
      category.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 82690 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const category: ICategory = { id: 456 };
      const pays: IPays = { id: 42247 };
      category.pays = pays;
      const natureConfig: INatureConfig = { id: 41943 };
      category.natureConfig = natureConfig;
      const affilication: IAffiliation = { id: 45608 };
      category.affilication = affilication;
      const entreprise: IEntreprise = { id: 8825 };
      category.entreprise = entreprise;
      const groupe: IGroupe = { id: 43405 };
      category.groupe = groupe;

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(category));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.natureConfigsSharedCollection).toContain(natureConfig);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Category>>();
      const category = { id: 123 };
      jest.spyOn(categoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: category }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoryService.update).toHaveBeenCalledWith(category);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Category>>();
      const category = new Category();
      jest.spyOn(categoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: category }));
      saveSubject.complete();

      // THEN
      expect(categoryService.create).toHaveBeenCalledWith(category);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Category>>();
      const category = { id: 123 };
      jest.spyOn(categoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoryService.update).toHaveBeenCalledWith(category);
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

    describe('trackNatureConfigById', () => {
      it('Should return tracked NatureConfig primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureConfigById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAffiliationById', () => {
      it('Should return tracked Affiliation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffiliationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGroupeById', () => {
      it('Should return tracked Groupe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGroupeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
