jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EchlonService } from '../service/echlon.service';
import { IEchlon, Echlon } from '../echlon.model';
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

import { EchlonUpdateComponent } from './echlon-update.component';

describe('Echlon Management Update Component', () => {
  let comp: EchlonUpdateComponent;
  let fixture: ComponentFixture<EchlonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let echlonService: EchlonService;
  let paysService: PaysService;
  let natureConfigService: NatureConfigService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EchlonUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EchlonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EchlonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    echlonService = TestBed.inject(EchlonService);
    paysService = TestBed.inject(PaysService);
    natureConfigService = TestBed.inject(NatureConfigService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const echlon: IEchlon = { id: 456 };
      const pays: IPays = { id: 95772 };
      echlon.pays = pays;

      const paysCollection: IPays[] = [{ id: 8926 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureConfig query and add missing value', () => {
      const echlon: IEchlon = { id: 456 };
      const natureConfig: INatureConfig = { id: 33807 };
      echlon.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 46368 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const echlon: IEchlon = { id: 456 };
      const affilication: IAffiliation = { id: 84310 };
      echlon.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 38507 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const echlon: IEchlon = { id: 456 };
      const entreprise: IEntreprise = { id: 92431 };
      echlon.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 28324 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const echlon: IEchlon = { id: 456 };
      const groupe: IGroupe = { id: 35196 };
      echlon.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 85402 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const echlon: IEchlon = { id: 456 };
      const pays: IPays = { id: 42874 };
      echlon.pays = pays;
      const natureConfig: INatureConfig = { id: 35367 };
      echlon.natureConfig = natureConfig;
      const affilication: IAffiliation = { id: 53162 };
      echlon.affilication = affilication;
      const entreprise: IEntreprise = { id: 41336 };
      echlon.entreprise = entreprise;
      const groupe: IGroupe = { id: 17950 };
      echlon.groupe = groupe;

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(echlon));
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
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = { id: 123 };
      jest.spyOn(echlonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: echlon }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(echlonService.update).toHaveBeenCalledWith(echlon);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = new Echlon();
      jest.spyOn(echlonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: echlon }));
      saveSubject.complete();

      // THEN
      expect(echlonService.create).toHaveBeenCalledWith(echlon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = { id: 123 };
      jest.spyOn(echlonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(echlonService.update).toHaveBeenCalledWith(echlon);
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
