jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureAbsenceService } from '../service/nature-absence.service';
import { INatureAbsence, NatureAbsence } from '../nature-absence.model';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { NatureConfigService } from 'app/entities/nature-config/service/nature-config.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';

import { NatureAbsenceUpdateComponent } from './nature-absence-update.component';

describe('NatureAbsence Management Update Component', () => {
  let comp: NatureAbsenceUpdateComponent;
  let fixture: ComponentFixture<NatureAbsenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureAbsenceService: NatureAbsenceService;
  let natureConfigService: NatureConfigService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let sexeService: SexeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureAbsenceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureAbsenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureAbsenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);
    natureConfigService = TestBed.inject(NatureConfigService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    sexeService = TestBed.inject(SexeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NatureConfig query and add missing value', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const natureConfig: INatureConfig = { id: 24713 };
      natureAbsence.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 62105 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const affilication: IAffiliation = { id: 5629 };
      natureAbsence.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 59562 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const entreprise: IEntreprise = { id: 14455 };
      natureAbsence.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 47652 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const groupe: IGroupe = { id: 69758 };
      natureAbsence.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 37045 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sexe query and add missing value', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const sexe: ISexe = { id: 13994 };
      natureAbsence.sexe = sexe;

      const sexeCollection: ISexe[] = [{ id: 18455 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexes = [sexe];
      const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const natureAbsence: INatureAbsence = { id: 456 };
      const natureConfig: INatureConfig = { id: 68441 };
      natureAbsence.natureConfig = natureConfig;
      const affilication: IAffiliation = { id: 6331 };
      natureAbsence.affilication = affilication;
      const entreprise: IEntreprise = { id: 59682 };
      natureAbsence.entreprise = entreprise;
      const groupe: IGroupe = { id: 46122 };
      natureAbsence.groupe = groupe;
      const sexe: ISexe = { id: 65098 };
      natureAbsence.sexe = sexe;

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureAbsence));
      expect(comp.natureConfigsSharedCollection).toContain(natureConfig);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.sexesSharedCollection).toContain(sexe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = { id: 123 };
      jest.spyOn(natureAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsence }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureAbsenceService.update).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = new NatureAbsence();
      jest.spyOn(natureAbsenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsence }));
      saveSubject.complete();

      // THEN
      expect(natureAbsenceService.create).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = { id: 123 };
      jest.spyOn(natureAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureAbsenceService.update).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
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

    describe('trackSexeById', () => {
      it('Should return tracked Sexe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSexeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
