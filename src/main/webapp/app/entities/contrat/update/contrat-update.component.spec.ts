jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContratService } from '../service/contrat.service';
import { IContrat, Contrat } from '../contrat.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { SousTypeContratService } from 'app/entities/sous-type-contrat/service/sous-type-contrat.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

import { ContratUpdateComponent } from './contrat-update.component';

describe('Contrat Management Update Component', () => {
  let comp: ContratUpdateComponent;
  let fixture: ComponentFixture<ContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratService: ContratService;
  let employeService: EmployeService;
  let sousTypeContratService: SousTypeContratService;
  let groupeService: GroupeService;
  let entrepriseService: EntrepriseService;
  let affiliationService: AffiliationService;
  let deviseService: DeviseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ContratUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratService = TestBed.inject(ContratService);
    employeService = TestBed.inject(EmployeService);
    sousTypeContratService = TestBed.inject(SousTypeContratService);
    groupeService = TestBed.inject(GroupeService);
    entrepriseService = TestBed.inject(EntrepriseService);
    affiliationService = TestBed.inject(AffiliationService);
    deviseService = TestBed.inject(DeviseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employe query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const employe: IEmploye = { id: 34608 };
      contrat.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 2424 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SousTypeContrat query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const sousType: ISousTypeContrat = { id: 71019 };
      contrat.sousType = sousType;

      const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 88451 }];
      jest.spyOn(sousTypeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: sousTypeContratCollection })));
      const additionalSousTypeContrats = [sousType];
      const expectedCollection: ISousTypeContrat[] = [...additionalSousTypeContrats, ...sousTypeContratCollection];
      jest.spyOn(sousTypeContratService, 'addSousTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(sousTypeContratService.query).toHaveBeenCalled();
      expect(sousTypeContratService.addSousTypeContratToCollectionIfMissing).toHaveBeenCalledWith(
        sousTypeContratCollection,
        ...additionalSousTypeContrats
      );
      expect(comp.sousTypeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const groupe: IGroupe = { id: 55998 };
      contrat.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 43158 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const entreprise: IEntreprise = { id: 65935 };
      contrat.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 17299 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const affiliation: IAffiliation = { id: 41076 };
      contrat.affiliation = affiliation;

      const affiliationCollection: IAffiliation[] = [{ id: 40023 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affiliation];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Devise query and add missing value', () => {
      const contrat: IContrat = { id: 456 };
      const devise: IDevise = { id: 27580 };
      contrat.devise = devise;

      const deviseCollection: IDevise[] = [{ id: 57597 }];
      jest.spyOn(deviseService, 'query').mockReturnValue(of(new HttpResponse({ body: deviseCollection })));
      const additionalDevises = [devise];
      const expectedCollection: IDevise[] = [...additionalDevises, ...deviseCollection];
      jest.spyOn(deviseService, 'addDeviseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(deviseService.query).toHaveBeenCalled();
      expect(deviseService.addDeviseToCollectionIfMissing).toHaveBeenCalledWith(deviseCollection, ...additionalDevises);
      expect(comp.devisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contrat: IContrat = { id: 456 };
      const employe: IEmploye = { id: 35545 };
      contrat.employe = employe;
      const sousType: ISousTypeContrat = { id: 33752 };
      contrat.sousType = sousType;
      const groupe: IGroupe = { id: 72256 };
      contrat.groupe = groupe;
      const entreprise: IEntreprise = { id: 14868 };
      contrat.entreprise = entreprise;
      const affiliation: IAffiliation = { id: 78479 };
      contrat.affiliation = affiliation;
      const devise: IDevise = { id: 45332 };
      contrat.devise = devise;

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contrat));
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.sousTypeContratsSharedCollection).toContain(sousType);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.affiliationsSharedCollection).toContain(affiliation);
      expect(comp.devisesSharedCollection).toContain(devise);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contrat>>();
      const contrat = { id: 123 };
      jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratService.update).toHaveBeenCalledWith(contrat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contrat>>();
      const contrat = new Contrat();
      jest.spyOn(contratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrat }));
      saveSubject.complete();

      // THEN
      expect(contratService.create).toHaveBeenCalledWith(contrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contrat>>();
      const contrat = { id: 123 };
      jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratService.update).toHaveBeenCalledWith(contrat);
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

    describe('trackSousTypeContratById', () => {
      it('Should return tracked SousTypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSousTypeContratById(0, entity);
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

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
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

    describe('trackDeviseById', () => {
      it('Should return tracked Devise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
