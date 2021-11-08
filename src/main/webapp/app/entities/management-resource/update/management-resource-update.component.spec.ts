jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ManagementResourceService } from '../service/management-resource.service';
import { IManagementResource, ManagementResource } from '../management-resource.model';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';

import { ManagementResourceUpdateComponent } from './management-resource-update.component';

describe('ManagementResource Management Update Component', () => {
  let comp: ManagementResourceUpdateComponent;
  let fixture: ComponentFixture<ManagementResourceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let managementResourceService: ManagementResourceService;
  let situationService: SituationService;
  let employeService: EmployeService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let affiliationService: AffiliationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ManagementResourceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ManagementResourceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ManagementResourceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    managementResourceService = TestBed.inject(ManagementResourceService);
    situationService = TestBed.inject(SituationService);
    employeService = TestBed.inject(EmployeService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    affiliationService = TestBed.inject(AffiliationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Situation query and add missing value', () => {
      const managementResource: IManagementResource = { id: 456 };
      const situation: ISituation = { id: 93972 };
      managementResource.situation = situation;

      const situationCollection: ISituation[] = [{ id: 44482 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employe query and add missing value', () => {
      const managementResource: IManagementResource = { id: 456 };
      const employe: IEmploye = { id: 85106 };
      managementResource.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 96651 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const managementResource: IManagementResource = { id: 456 };
      const entreprise: IEntreprise = { id: 82695 };
      managementResource.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 84875 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const managementResource: IManagementResource = { id: 456 };
      const groupe: IGroupe = { id: 50808 };
      managementResource.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 41525 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const managementResource: IManagementResource = { id: 456 };
      const affiliation: IAffiliation = { id: 53972 };
      managementResource.affiliation = affiliation;

      const affiliationCollection: IAffiliation[] = [{ id: 75799 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affiliation];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const managementResource: IManagementResource = { id: 456 };
      const situation: ISituation = { id: 82669 };
      managementResource.situation = situation;
      const employe: IEmploye = { id: 9151 };
      managementResource.employe = employe;
      const entreprise: IEntreprise = { id: 2414 };
      managementResource.entreprise = entreprise;
      const groupe: IGroupe = { id: 22843 };
      managementResource.groupe = groupe;
      const affiliation: IAffiliation = { id: 67597 };
      managementResource.affiliation = affiliation;

      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(managementResource));
      expect(comp.situationsSharedCollection).toContain(situation);
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.affiliationsSharedCollection).toContain(affiliation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResource>>();
      const managementResource = { id: 123 };
      jest.spyOn(managementResourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResource }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(managementResourceService.update).toHaveBeenCalledWith(managementResource);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResource>>();
      const managementResource = new ManagementResource();
      jest.spyOn(managementResourceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResource }));
      saveSubject.complete();

      // THEN
      expect(managementResourceService.create).toHaveBeenCalledWith(managementResource);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResource>>();
      const managementResource = { id: 123 };
      jest.spyOn(managementResourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResource });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(managementResourceService.update).toHaveBeenCalledWith(managementResource);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmployeById', () => {
      it('Should return tracked Employe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeById(0, entity);
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

    describe('trackAffiliationById', () => {
      it('Should return tracked Affiliation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffiliationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
