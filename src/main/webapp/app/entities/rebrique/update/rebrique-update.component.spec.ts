jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RebriqueService } from '../service/rebrique.service';
import { IRebrique, Rebrique } from '../rebrique.model';
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
import { IModeInput } from 'app/entities/mode-input/mode-input.model';
import { ModeInputService } from 'app/entities/mode-input/service/mode-input.service';
import { ISens } from 'app/entities/sens/sens.model';
import { SensService } from 'app/entities/sens/service/sens.service';
import { IConcerne } from 'app/entities/concerne/concerne.model';
import { ConcerneService } from 'app/entities/concerne/service/concerne.service';
import { IFrequence } from 'app/entities/frequence/frequence.model';
import { FrequenceService } from 'app/entities/frequence/service/frequence.service';

import { RebriqueUpdateComponent } from './rebrique-update.component';

describe('Rebrique Management Update Component', () => {
  let comp: RebriqueUpdateComponent;
  let fixture: ComponentFixture<RebriqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rebriqueService: RebriqueService;
  let paysService: PaysService;
  let natureConfigService: NatureConfigService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let modeInputService: ModeInputService;
  let sensService: SensService;
  let concerneService: ConcerneService;
  let frequenceService: FrequenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RebriqueUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RebriqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RebriqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rebriqueService = TestBed.inject(RebriqueService);
    paysService = TestBed.inject(PaysService);
    natureConfigService = TestBed.inject(NatureConfigService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    modeInputService = TestBed.inject(ModeInputService);
    sensService = TestBed.inject(SensService);
    concerneService = TestBed.inject(ConcerneService);
    frequenceService = TestBed.inject(FrequenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const pays: IPays = { id: 89060 };
      rebrique.pays = pays;

      const paysCollection: IPays[] = [{ id: 6486 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureConfig query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const natureConfig: INatureConfig = { id: 99246 };
      rebrique.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 40223 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const affilication: IAffiliation = { id: 63749 };
      rebrique.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 44807 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const entreprise: IEntreprise = { id: 4558 };
      rebrique.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 4648 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const groupe: IGroupe = { id: 14052 };
      rebrique.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 32285 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ModeInput query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const modeInput: IModeInput = { id: 62511 };
      rebrique.modeInput = modeInput;

      const modeInputCollection: IModeInput[] = [{ id: 6682 }];
      jest.spyOn(modeInputService, 'query').mockReturnValue(of(new HttpResponse({ body: modeInputCollection })));
      const additionalModeInputs = [modeInput];
      const expectedCollection: IModeInput[] = [...additionalModeInputs, ...modeInputCollection];
      jest.spyOn(modeInputService, 'addModeInputToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(modeInputService.query).toHaveBeenCalled();
      expect(modeInputService.addModeInputToCollectionIfMissing).toHaveBeenCalledWith(modeInputCollection, ...additionalModeInputs);
      expect(comp.modeInputsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sens query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const sens: ISens = { id: 60635 };
      rebrique.sens = sens;

      const sensCollection: ISens[] = [{ id: 10161 }];
      jest.spyOn(sensService, 'query').mockReturnValue(of(new HttpResponse({ body: sensCollection })));
      const additionalSens = [sens];
      const expectedCollection: ISens[] = [...additionalSens, ...sensCollection];
      jest.spyOn(sensService, 'addSensToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(sensService.query).toHaveBeenCalled();
      expect(sensService.addSensToCollectionIfMissing).toHaveBeenCalledWith(sensCollection, ...additionalSens);
      expect(comp.sensSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Concerne query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const concerne: IConcerne = { id: 32510 };
      rebrique.concerne = concerne;

      const concerneCollection: IConcerne[] = [{ id: 18913 }];
      jest.spyOn(concerneService, 'query').mockReturnValue(of(new HttpResponse({ body: concerneCollection })));
      const additionalConcernes = [concerne];
      const expectedCollection: IConcerne[] = [...additionalConcernes, ...concerneCollection];
      jest.spyOn(concerneService, 'addConcerneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(concerneService.query).toHaveBeenCalled();
      expect(concerneService.addConcerneToCollectionIfMissing).toHaveBeenCalledWith(concerneCollection, ...additionalConcernes);
      expect(comp.concernesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Frequence query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const frequence: IFrequence = { id: 23102 };
      rebrique.frequence = frequence;

      const frequenceCollection: IFrequence[] = [{ id: 23141 }];
      jest.spyOn(frequenceService, 'query').mockReturnValue(of(new HttpResponse({ body: frequenceCollection })));
      const additionalFrequences = [frequence];
      const expectedCollection: IFrequence[] = [...additionalFrequences, ...frequenceCollection];
      jest.spyOn(frequenceService, 'addFrequenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(frequenceService.query).toHaveBeenCalled();
      expect(frequenceService.addFrequenceToCollectionIfMissing).toHaveBeenCalledWith(frequenceCollection, ...additionalFrequences);
      expect(comp.frequencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rebrique: IRebrique = { id: 456 };
      const pays: IPays = { id: 27722 };
      rebrique.pays = pays;
      const natureConfig: INatureConfig = { id: 81663 };
      rebrique.natureConfig = natureConfig;
      const affilication: IAffiliation = { id: 72997 };
      rebrique.affilication = affilication;
      const entreprise: IEntreprise = { id: 41643 };
      rebrique.entreprise = entreprise;
      const groupe: IGroupe = { id: 20663 };
      rebrique.groupe = groupe;
      const modeInput: IModeInput = { id: 8292 };
      rebrique.modeInput = modeInput;
      const sens: ISens = { id: 52290 };
      rebrique.sens = sens;
      const concerne: IConcerne = { id: 41044 };
      rebrique.concerne = concerne;
      const frequence: IFrequence = { id: 32874 };
      rebrique.frequence = frequence;

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rebrique));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.natureConfigsSharedCollection).toContain(natureConfig);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.modeInputsSharedCollection).toContain(modeInput);
      expect(comp.sensSharedCollection).toContain(sens);
      expect(comp.concernesSharedCollection).toContain(concerne);
      expect(comp.frequencesSharedCollection).toContain(frequence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = { id: 123 };
      jest.spyOn(rebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebrique }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rebriqueService.update).toHaveBeenCalledWith(rebrique);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = new Rebrique();
      jest.spyOn(rebriqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebrique }));
      saveSubject.complete();

      // THEN
      expect(rebriqueService.create).toHaveBeenCalledWith(rebrique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = { id: 123 };
      jest.spyOn(rebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rebriqueService.update).toHaveBeenCalledWith(rebrique);
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

    describe('trackModeInputById', () => {
      it('Should return tracked ModeInput primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModeInputById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSensById', () => {
      it('Should return tracked Sens primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSensById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackConcerneById', () => {
      it('Should return tracked Concerne primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackConcerneById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFrequenceById', () => {
      it('Should return tracked Frequence primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFrequenceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
