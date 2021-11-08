jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PlateService } from '../service/plate.service';
import { IPlate, Plate } from '../plate.model';
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
import { IModeCalcul } from 'app/entities/mode-calcul/mode-calcul.model';
import { ModeCalculService } from 'app/entities/mode-calcul/service/mode-calcul.service';

import { PlateUpdateComponent } from './plate-update.component';

describe('Plate Management Update Component', () => {
  let comp: PlateUpdateComponent;
  let fixture: ComponentFixture<PlateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plateService: PlateService;
  let paysService: PaysService;
  let natureConfigService: NatureConfigService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let modeCalculService: ModeCalculService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlateUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PlateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plateService = TestBed.inject(PlateService);
    paysService = TestBed.inject(PaysService);
    natureConfigService = TestBed.inject(NatureConfigService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    modeCalculService = TestBed.inject(ModeCalculService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const pays: IPays = { id: 22340 };
      plate.pays = pays;

      const paysCollection: IPays[] = [{ id: 58394 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureConfig query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const natureConfig: INatureConfig = { id: 48020 };
      plate.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 93775 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const affilication: IAffiliation = { id: 31720 };
      plate.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 40527 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const entreprise: IEntreprise = { id: 82755 };
      plate.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 32 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const groupe: IGroupe = { id: 4772 };
      plate.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 10638 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ModeCalcul query and add missing value', () => {
      const plate: IPlate = { id: 456 };
      const modeCalcul: IModeCalcul = { id: 51651 };
      plate.modeCalcul = modeCalcul;

      const modeCalculCollection: IModeCalcul[] = [{ id: 58070 }];
      jest.spyOn(modeCalculService, 'query').mockReturnValue(of(new HttpResponse({ body: modeCalculCollection })));
      const additionalModeCalculs = [modeCalcul];
      const expectedCollection: IModeCalcul[] = [...additionalModeCalculs, ...modeCalculCollection];
      jest.spyOn(modeCalculService, 'addModeCalculToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(modeCalculService.query).toHaveBeenCalled();
      expect(modeCalculService.addModeCalculToCollectionIfMissing).toHaveBeenCalledWith(modeCalculCollection, ...additionalModeCalculs);
      expect(comp.modeCalculsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plate: IPlate = { id: 456 };
      const pays: IPays = { id: 94507 };
      plate.pays = pays;
      const natureConfig: INatureConfig = { id: 35208 };
      plate.natureConfig = natureConfig;
      const affilication: IAffiliation = { id: 51077 };
      plate.affilication = affilication;
      const entreprise: IEntreprise = { id: 98237 };
      plate.entreprise = entreprise;
      const groupe: IGroupe = { id: 80172 };
      plate.groupe = groupe;
      const modeCalcul: IModeCalcul = { id: 12750 };
      plate.modeCalcul = modeCalcul;

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(plate));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.natureConfigsSharedCollection).toContain(natureConfig);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.modeCalculsSharedCollection).toContain(modeCalcul);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plate>>();
      const plate = { id: 123 };
      jest.spyOn(plateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(plateService.update).toHaveBeenCalledWith(plate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plate>>();
      const plate = new Plate();
      jest.spyOn(plateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plate }));
      saveSubject.complete();

      // THEN
      expect(plateService.create).toHaveBeenCalledWith(plate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plate>>();
      const plate = { id: 123 };
      jest.spyOn(plateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plateService.update).toHaveBeenCalledWith(plate);
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

    describe('trackModeCalculById', () => {
      it('Should return tracked ModeCalcul primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModeCalculById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
