jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EligibiliteService } from '../service/eligibilite.service';
import { IEligibilite, Eligibilite } from '../eligibilite.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { NatureConfigService } from 'app/entities/nature-config/service/nature-config.service';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { EchlonService } from 'app/entities/echlon/service/echlon.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { EmploiService } from 'app/entities/emploi/service/emploi.service';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { TypeHandicapService } from 'app/entities/type-handicap/service/type-handicap.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { SousTypeContratService } from 'app/entities/sous-type-contrat/service/sous-type-contrat.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';
import { INatureEligibilite } from 'app/entities/nature-eligibilite/nature-eligibilite.model';
import { NatureEligibiliteService } from 'app/entities/nature-eligibilite/service/nature-eligibilite.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';
import { IPlate } from 'app/entities/plate/plate.model';
import { PlateService } from 'app/entities/plate/service/plate.service';
import { ITargetEligible } from 'app/entities/target-eligible/target-eligible.model';
import { TargetEligibleService } from 'app/entities/target-eligible/service/target-eligible.service';

import { EligibiliteUpdateComponent } from './eligibilite-update.component';

describe('Eligibilite Management Update Component', () => {
  let comp: EligibiliteUpdateComponent;
  let fixture: ComponentFixture<EligibiliteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eligibiliteService: EligibiliteService;
  let paysService: PaysService;
  let natureConfigService: NatureConfigService;
  let echlonService: EchlonService;
  let categoryService: CategoryService;
  let emploiService: EmploiService;
  let typeHandicapService: TypeHandicapService;
  let sexeService: SexeService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let typeContratService: TypeContratService;
  let sousTypeContratService: SousTypeContratService;
  let niveauEtudeService: NiveauEtudeService;
  let natureEligibiliteService: NatureEligibiliteService;
  let rebriqueService: RebriqueService;
  let natureAbsenceService: NatureAbsenceService;
  let plateService: PlateService;
  let targetEligibleService: TargetEligibleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EligibiliteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EligibiliteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EligibiliteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eligibiliteService = TestBed.inject(EligibiliteService);
    paysService = TestBed.inject(PaysService);
    natureConfigService = TestBed.inject(NatureConfigService);
    echlonService = TestBed.inject(EchlonService);
    categoryService = TestBed.inject(CategoryService);
    emploiService = TestBed.inject(EmploiService);
    typeHandicapService = TestBed.inject(TypeHandicapService);
    sexeService = TestBed.inject(SexeService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    typeContratService = TestBed.inject(TypeContratService);
    sousTypeContratService = TestBed.inject(SousTypeContratService);
    niveauEtudeService = TestBed.inject(NiveauEtudeService);
    natureEligibiliteService = TestBed.inject(NatureEligibiliteService);
    rebriqueService = TestBed.inject(RebriqueService);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);
    plateService = TestBed.inject(PlateService);
    targetEligibleService = TestBed.inject(TargetEligibleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const pays: IPays = { id: 42817 };
      eligibilite.pays = pays;
      const nationalite: IPays = { id: 18812 };
      eligibilite.nationalite = nationalite;

      const paysCollection: IPays[] = [{ id: 99949 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays, nationalite];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureConfig query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const natureConfig: INatureConfig = { id: 57906 };
      eligibilite.natureConfig = natureConfig;

      const natureConfigCollection: INatureConfig[] = [{ id: 4044 }];
      jest.spyOn(natureConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: natureConfigCollection })));
      const additionalNatureConfigs = [natureConfig];
      const expectedCollection: INatureConfig[] = [...additionalNatureConfigs, ...natureConfigCollection];
      jest.spyOn(natureConfigService, 'addNatureConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(natureConfigService.query).toHaveBeenCalled();
      expect(natureConfigService.addNatureConfigToCollectionIfMissing).toHaveBeenCalledWith(
        natureConfigCollection,
        ...additionalNatureConfigs
      );
      expect(comp.natureConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Echlon query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const echlon: IEchlon = { id: 58790 };
      eligibilite.echlon = echlon;

      const echlonCollection: IEchlon[] = [{ id: 28948 }];
      jest.spyOn(echlonService, 'query').mockReturnValue(of(new HttpResponse({ body: echlonCollection })));
      const additionalEchlons = [echlon];
      const expectedCollection: IEchlon[] = [...additionalEchlons, ...echlonCollection];
      jest.spyOn(echlonService, 'addEchlonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(echlonService.query).toHaveBeenCalled();
      expect(echlonService.addEchlonToCollectionIfMissing).toHaveBeenCalledWith(echlonCollection, ...additionalEchlons);
      expect(comp.echlonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const category: ICategory = { id: 32403 };
      eligibilite.category = category;

      const categoryCollection: ICategory[] = [{ id: 66520 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Emploi query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const emploi: IEmploi = { id: 87749 };
      eligibilite.emploi = emploi;

      const emploiCollection: IEmploi[] = [{ id: 50415 }];
      jest.spyOn(emploiService, 'query').mockReturnValue(of(new HttpResponse({ body: emploiCollection })));
      const additionalEmplois = [emploi];
      const expectedCollection: IEmploi[] = [...additionalEmplois, ...emploiCollection];
      jest.spyOn(emploiService, 'addEmploiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(emploiService.query).toHaveBeenCalled();
      expect(emploiService.addEmploiToCollectionIfMissing).toHaveBeenCalledWith(emploiCollection, ...additionalEmplois);
      expect(comp.emploisSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeHandicap query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const typeHandicap: ITypeHandicap = { id: 71722 };
      eligibilite.typeHandicap = typeHandicap;

      const typeHandicapCollection: ITypeHandicap[] = [{ id: 90017 }];
      jest.spyOn(typeHandicapService, 'query').mockReturnValue(of(new HttpResponse({ body: typeHandicapCollection })));
      const additionalTypeHandicaps = [typeHandicap];
      const expectedCollection: ITypeHandicap[] = [...additionalTypeHandicaps, ...typeHandicapCollection];
      jest.spyOn(typeHandicapService, 'addTypeHandicapToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(typeHandicapService.query).toHaveBeenCalled();
      expect(typeHandicapService.addTypeHandicapToCollectionIfMissing).toHaveBeenCalledWith(
        typeHandicapCollection,
        ...additionalTypeHandicaps
      );
      expect(comp.typeHandicapsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sexe query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const sexe: ISexe = { id: 16268 };
      eligibilite.sexe = sexe;

      const sexeCollection: ISexe[] = [{ id: 50436 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexes = [sexe];
      const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const affilication: IAffiliation = { id: 96592 };
      eligibilite.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 26250 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const entreprise: IEntreprise = { id: 15248 };
      eligibilite.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 18471 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const groupe: IGroupe = { id: 81244 };
      eligibilite.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 84537 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeContrat query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const typeContrat: ITypeContrat = { id: 41000 };
      eligibilite.typeContrat = typeContrat;

      const typeContratCollection: ITypeContrat[] = [{ id: 23634 }];
      jest.spyOn(typeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: typeContratCollection })));
      const additionalTypeContrats = [typeContrat];
      const expectedCollection: ITypeContrat[] = [...additionalTypeContrats, ...typeContratCollection];
      jest.spyOn(typeContratService, 'addTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(typeContratService.query).toHaveBeenCalled();
      expect(typeContratService.addTypeContratToCollectionIfMissing).toHaveBeenCalledWith(typeContratCollection, ...additionalTypeContrats);
      expect(comp.typeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SousTypeContrat query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const sousTypeContrat: ISousTypeContrat = { id: 47045 };
      eligibilite.sousTypeContrat = sousTypeContrat;

      const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 80280 }];
      jest.spyOn(sousTypeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: sousTypeContratCollection })));
      const additionalSousTypeContrats = [sousTypeContrat];
      const expectedCollection: ISousTypeContrat[] = [...additionalSousTypeContrats, ...sousTypeContratCollection];
      jest.spyOn(sousTypeContratService, 'addSousTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(sousTypeContratService.query).toHaveBeenCalled();
      expect(sousTypeContratService.addSousTypeContratToCollectionIfMissing).toHaveBeenCalledWith(
        sousTypeContratCollection,
        ...additionalSousTypeContrats
      );
      expect(comp.sousTypeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NiveauEtude query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const niveauEtude: INiveauEtude = { id: 40185 };
      eligibilite.niveauEtude = niveauEtude;

      const niveauEtudeCollection: INiveauEtude[] = [{ id: 99345 }];
      jest.spyOn(niveauEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: niveauEtudeCollection })));
      const additionalNiveauEtudes = [niveauEtude];
      const expectedCollection: INiveauEtude[] = [...additionalNiveauEtudes, ...niveauEtudeCollection];
      jest.spyOn(niveauEtudeService, 'addNiveauEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(niveauEtudeService.query).toHaveBeenCalled();
      expect(niveauEtudeService.addNiveauEtudeToCollectionIfMissing).toHaveBeenCalledWith(niveauEtudeCollection, ...additionalNiveauEtudes);
      expect(comp.niveauEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureEligibilite query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const natureEligible: INatureEligibilite = { id: 14193 };
      eligibilite.natureEligible = natureEligible;

      const natureEligibiliteCollection: INatureEligibilite[] = [{ id: 29202 }];
      jest.spyOn(natureEligibiliteService, 'query').mockReturnValue(of(new HttpResponse({ body: natureEligibiliteCollection })));
      const additionalNatureEligibilites = [natureEligible];
      const expectedCollection: INatureEligibilite[] = [...additionalNatureEligibilites, ...natureEligibiliteCollection];
      jest.spyOn(natureEligibiliteService, 'addNatureEligibiliteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(natureEligibiliteService.query).toHaveBeenCalled();
      expect(natureEligibiliteService.addNatureEligibiliteToCollectionIfMissing).toHaveBeenCalledWith(
        natureEligibiliteCollection,
        ...additionalNatureEligibilites
      );
      expect(comp.natureEligibilitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Rebrique query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const rebrique: IRebrique = { id: 89431 };
      eligibilite.rebrique = rebrique;

      const rebriqueCollection: IRebrique[] = [{ id: 32658 }];
      jest.spyOn(rebriqueService, 'query').mockReturnValue(of(new HttpResponse({ body: rebriqueCollection })));
      const additionalRebriques = [rebrique];
      const expectedCollection: IRebrique[] = [...additionalRebriques, ...rebriqueCollection];
      jest.spyOn(rebriqueService, 'addRebriqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(rebriqueService.query).toHaveBeenCalled();
      expect(rebriqueService.addRebriqueToCollectionIfMissing).toHaveBeenCalledWith(rebriqueCollection, ...additionalRebriques);
      expect(comp.rebriquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureAbsence query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const natureAbsence: INatureAbsence = { id: 35336 };
      eligibilite.natureAbsence = natureAbsence;

      const natureAbsenceCollection: INatureAbsence[] = [{ id: 71423 }];
      jest.spyOn(natureAbsenceService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAbsenceCollection })));
      const additionalNatureAbsences = [natureAbsence];
      const expectedCollection: INatureAbsence[] = [...additionalNatureAbsences, ...natureAbsenceCollection];
      jest.spyOn(natureAbsenceService, 'addNatureAbsenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(natureAbsenceService.query).toHaveBeenCalled();
      expect(natureAbsenceService.addNatureAbsenceToCollectionIfMissing).toHaveBeenCalledWith(
        natureAbsenceCollection,
        ...additionalNatureAbsences
      );
      expect(comp.natureAbsencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Plate query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const plate: IPlate = { id: 38759 };
      eligibilite.plate = plate;

      const plateCollection: IPlate[] = [{ id: 66160 }];
      jest.spyOn(plateService, 'query').mockReturnValue(of(new HttpResponse({ body: plateCollection })));
      const additionalPlates = [plate];
      const expectedCollection: IPlate[] = [...additionalPlates, ...plateCollection];
      jest.spyOn(plateService, 'addPlateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(plateService.query).toHaveBeenCalled();
      expect(plateService.addPlateToCollectionIfMissing).toHaveBeenCalledWith(plateCollection, ...additionalPlates);
      expect(comp.platesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TargetEligible query and add missing value', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const targetEnt: ITargetEligible = { id: 81189 };
      eligibilite.targetEnt = targetEnt;

      const targetEligibleCollection: ITargetEligible[] = [{ id: 23044 }];
      jest.spyOn(targetEligibleService, 'query').mockReturnValue(of(new HttpResponse({ body: targetEligibleCollection })));
      const additionalTargetEligibles = [targetEnt];
      const expectedCollection: ITargetEligible[] = [...additionalTargetEligibles, ...targetEligibleCollection];
      jest.spyOn(targetEligibleService, 'addTargetEligibleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(targetEligibleService.query).toHaveBeenCalled();
      expect(targetEligibleService.addTargetEligibleToCollectionIfMissing).toHaveBeenCalledWith(
        targetEligibleCollection,
        ...additionalTargetEligibles
      );
      expect(comp.targetEligiblesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eligibilite: IEligibilite = { id: 456 };
      const pays: IPays = { id: 21161 };
      eligibilite.pays = pays;
      const nationalite: IPays = { id: 77969 };
      eligibilite.nationalite = nationalite;
      const natureConfig: INatureConfig = { id: 44281 };
      eligibilite.natureConfig = natureConfig;
      const echlon: IEchlon = { id: 61101 };
      eligibilite.echlon = echlon;
      const category: ICategory = { id: 62553 };
      eligibilite.category = category;
      const emploi: IEmploi = { id: 29036 };
      eligibilite.emploi = emploi;
      const typeHandicap: ITypeHandicap = { id: 34463 };
      eligibilite.typeHandicap = typeHandicap;
      const sexe: ISexe = { id: 73906 };
      eligibilite.sexe = sexe;
      const affilication: IAffiliation = { id: 49198 };
      eligibilite.affilication = affilication;
      const entreprise: IEntreprise = { id: 51079 };
      eligibilite.entreprise = entreprise;
      const groupe: IGroupe = { id: 71507 };
      eligibilite.groupe = groupe;
      const typeContrat: ITypeContrat = { id: 68765 };
      eligibilite.typeContrat = typeContrat;
      const sousTypeContrat: ISousTypeContrat = { id: 71410 };
      eligibilite.sousTypeContrat = sousTypeContrat;
      const niveauEtude: INiveauEtude = { id: 42052 };
      eligibilite.niveauEtude = niveauEtude;
      const natureEligible: INatureEligibilite = { id: 87373 };
      eligibilite.natureEligible = natureEligible;
      const rebrique: IRebrique = { id: 79496 };
      eligibilite.rebrique = rebrique;
      const natureAbsence: INatureAbsence = { id: 27397 };
      eligibilite.natureAbsence = natureAbsence;
      const plate: IPlate = { id: 59551 };
      eligibilite.plate = plate;
      const targetEnt: ITargetEligible = { id: 79742 };
      eligibilite.targetEnt = targetEnt;

      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(eligibilite));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.paysSharedCollection).toContain(nationalite);
      expect(comp.natureConfigsSharedCollection).toContain(natureConfig);
      expect(comp.echlonsSharedCollection).toContain(echlon);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.emploisSharedCollection).toContain(emploi);
      expect(comp.typeHandicapsSharedCollection).toContain(typeHandicap);
      expect(comp.sexesSharedCollection).toContain(sexe);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.typeContratsSharedCollection).toContain(typeContrat);
      expect(comp.sousTypeContratsSharedCollection).toContain(sousTypeContrat);
      expect(comp.niveauEtudesSharedCollection).toContain(niveauEtude);
      expect(comp.natureEligibilitesSharedCollection).toContain(natureEligible);
      expect(comp.rebriquesSharedCollection).toContain(rebrique);
      expect(comp.natureAbsencesSharedCollection).toContain(natureAbsence);
      expect(comp.platesSharedCollection).toContain(plate);
      expect(comp.targetEligiblesSharedCollection).toContain(targetEnt);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eligibilite>>();
      const eligibilite = { id: 123 };
      jest.spyOn(eligibiliteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eligibilite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eligibiliteService.update).toHaveBeenCalledWith(eligibilite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eligibilite>>();
      const eligibilite = new Eligibilite();
      jest.spyOn(eligibiliteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eligibilite }));
      saveSubject.complete();

      // THEN
      expect(eligibiliteService.create).toHaveBeenCalledWith(eligibilite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eligibilite>>();
      const eligibilite = { id: 123 };
      jest.spyOn(eligibiliteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eligibiliteService.update).toHaveBeenCalledWith(eligibilite);
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

    describe('trackTypeHandicapById', () => {
      it('Should return tracked TypeHandicap primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeHandicapById(0, entity);
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

    describe('trackTypeContratById', () => {
      it('Should return tracked TypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeContratById(0, entity);
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

    describe('trackNiveauEtudeById', () => {
      it('Should return tracked NiveauEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNiveauEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNatureEligibiliteById', () => {
      it('Should return tracked NatureEligibilite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureEligibiliteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRebriqueById', () => {
      it('Should return tracked Rebrique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRebriqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNatureAbsenceById', () => {
      it('Should return tracked NatureAbsence primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureAbsenceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPlateById', () => {
      it('Should return tracked Plate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTargetEligibleById', () => {
      it('Should return tracked TargetEligible primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTargetEligibleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
