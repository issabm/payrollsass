import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEligibilite, Eligibilite } from '../eligibilite.model';
import { EligibiliteService } from '../service/eligibilite.service';
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

@Component({
  selector: 'jhi-eligibilite-update',
  templateUrl: './eligibilite-update.component.html',
})
export class EligibiliteUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  natureConfigsSharedCollection: INatureConfig[] = [];
  echlonsSharedCollection: IEchlon[] = [];
  categoriesSharedCollection: ICategory[] = [];
  emploisSharedCollection: IEmploi[] = [];
  typeHandicapsSharedCollection: ITypeHandicap[] = [];
  sexesSharedCollection: ISexe[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  typeContratsSharedCollection: ITypeContrat[] = [];
  sousTypeContratsSharedCollection: ISousTypeContrat[] = [];
  niveauEtudesSharedCollection: INiveauEtude[] = [];
  natureEligibilitesSharedCollection: INatureEligibilite[] = [];
  rebriquesSharedCollection: IRebrique[] = [];
  natureAbsencesSharedCollection: INatureAbsence[] = [];
  platesSharedCollection: IPlate[] = [];
  targetEligiblesSharedCollection: ITargetEligible[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    annee: [],
    mois: [],
    nbEnt: [],
    ageEnt: [],
    matricule: [],
    code: [],
    libEn: [],
    libAr: [],
    libFr: [],
    valPayroll: [],
    nbDaysLeave: [],
    pourValPayroll: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    pays: [],
    natureConfig: [],
    echlon: [],
    category: [],
    emploi: [],
    typeHandicap: [],
    nationalite: [],
    sexe: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    typeContrat: [],
    sousTypeContrat: [],
    niveauEtude: [],
    natureEligible: [],
    rebrique: [],
    natureAbsence: [],
    plate: [],
    targetEnt: [],
  });

  constructor(
    protected eligibiliteService: EligibiliteService,
    protected paysService: PaysService,
    protected natureConfigService: NatureConfigService,
    protected echlonService: EchlonService,
    protected categoryService: CategoryService,
    protected emploiService: EmploiService,
    protected typeHandicapService: TypeHandicapService,
    protected sexeService: SexeService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected typeContratService: TypeContratService,
    protected sousTypeContratService: SousTypeContratService,
    protected niveauEtudeService: NiveauEtudeService,
    protected natureEligibiliteService: NatureEligibiliteService,
    protected rebriqueService: RebriqueService,
    protected natureAbsenceService: NatureAbsenceService,
    protected plateService: PlateService,
    protected targetEligibleService: TargetEligibleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eligibilite }) => {
      if (eligibilite.id === undefined) {
        const today = dayjs().startOf('day');
        eligibilite.dateop = today;
        eligibilite.createdDate = today;
        eligibilite.modifiedDate = today;
      }

      this.updateForm(eligibilite);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eligibilite = this.createFromForm();
    if (eligibilite.id !== undefined) {
      this.subscribeToSaveResponse(this.eligibiliteService.update(eligibilite));
    } else {
      this.subscribeToSaveResponse(this.eligibiliteService.create(eligibilite));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackNatureConfigById(index: number, item: INatureConfig): number {
    return item.id!;
  }

  trackEchlonById(index: number, item: IEchlon): number {
    return item.id!;
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  trackEmploiById(index: number, item: IEmploi): number {
    return item.id!;
  }

  trackTypeHandicapById(index: number, item: ITypeHandicap): number {
    return item.id!;
  }

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackTypeContratById(index: number, item: ITypeContrat): number {
    return item.id!;
  }

  trackSousTypeContratById(index: number, item: ISousTypeContrat): number {
    return item.id!;
  }

  trackNiveauEtudeById(index: number, item: INiveauEtude): number {
    return item.id!;
  }

  trackNatureEligibiliteById(index: number, item: INatureEligibilite): number {
    return item.id!;
  }

  trackRebriqueById(index: number, item: IRebrique): number {
    return item.id!;
  }

  trackNatureAbsenceById(index: number, item: INatureAbsence): number {
    return item.id!;
  }

  trackPlateById(index: number, item: IPlate): number {
    return item.id!;
  }

  trackTargetEligibleById(index: number, item: ITargetEligible): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEligibilite>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eligibilite: IEligibilite): void {
    this.editForm.patchValue({
      id: eligibilite.id,
      priorite: eligibilite.priorite,
      annee: eligibilite.annee,
      mois: eligibilite.mois,
      nbEnt: eligibilite.nbEnt,
      ageEnt: eligibilite.ageEnt,
      matricule: eligibilite.matricule,
      code: eligibilite.code,
      libEn: eligibilite.libEn,
      libAr: eligibilite.libAr,
      libFr: eligibilite.libFr,
      valPayroll: eligibilite.valPayroll,
      nbDaysLeave: eligibilite.nbDaysLeave,
      pourValPayroll: eligibilite.pourValPayroll,
      dateop: eligibilite.dateop ? eligibilite.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: eligibilite.modifiedBy,
      createdBy: eligibilite.createdBy,
      op: eligibilite.op,
      util: eligibilite.util,
      isDeleted: eligibilite.isDeleted,
      createdDate: eligibilite.createdDate ? eligibilite.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: eligibilite.modifiedDate ? eligibilite.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: eligibilite.pays,
      natureConfig: eligibilite.natureConfig,
      echlon: eligibilite.echlon,
      category: eligibilite.category,
      emploi: eligibilite.emploi,
      typeHandicap: eligibilite.typeHandicap,
      nationalite: eligibilite.nationalite,
      sexe: eligibilite.sexe,
      affilication: eligibilite.affilication,
      entreprise: eligibilite.entreprise,
      groupe: eligibilite.groupe,
      typeContrat: eligibilite.typeContrat,
      sousTypeContrat: eligibilite.sousTypeContrat,
      niveauEtude: eligibilite.niveauEtude,
      natureEligible: eligibilite.natureEligible,
      rebrique: eligibilite.rebrique,
      natureAbsence: eligibilite.natureAbsence,
      plate: eligibilite.plate,
      targetEnt: eligibilite.targetEnt,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(
      this.paysSharedCollection,
      eligibilite.pays,
      eligibilite.nationalite
    );
    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      eligibilite.natureConfig
    );
    this.echlonsSharedCollection = this.echlonService.addEchlonToCollectionIfMissing(this.echlonsSharedCollection, eligibilite.echlon);
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      eligibilite.category
    );
    this.emploisSharedCollection = this.emploiService.addEmploiToCollectionIfMissing(this.emploisSharedCollection, eligibilite.emploi);
    this.typeHandicapsSharedCollection = this.typeHandicapService.addTypeHandicapToCollectionIfMissing(
      this.typeHandicapsSharedCollection,
      eligibilite.typeHandicap
    );
    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, eligibilite.sexe);
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      eligibilite.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      eligibilite.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, eligibilite.groupe);
    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing(
      this.typeContratsSharedCollection,
      eligibilite.typeContrat
    );
    this.sousTypeContratsSharedCollection = this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(
      this.sousTypeContratsSharedCollection,
      eligibilite.sousTypeContrat
    );
    this.niveauEtudesSharedCollection = this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(
      this.niveauEtudesSharedCollection,
      eligibilite.niveauEtude
    );
    this.natureEligibilitesSharedCollection = this.natureEligibiliteService.addNatureEligibiliteToCollectionIfMissing(
      this.natureEligibilitesSharedCollection,
      eligibilite.natureEligible
    );
    this.rebriquesSharedCollection = this.rebriqueService.addRebriqueToCollectionIfMissing(
      this.rebriquesSharedCollection,
      eligibilite.rebrique
    );
    this.natureAbsencesSharedCollection = this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(
      this.natureAbsencesSharedCollection,
      eligibilite.natureAbsence
    );
    this.platesSharedCollection = this.plateService.addPlateToCollectionIfMissing(this.platesSharedCollection, eligibilite.plate);
    this.targetEligiblesSharedCollection = this.targetEligibleService.addTargetEligibleToCollectionIfMissing(
      this.targetEligiblesSharedCollection,
      eligibilite.targetEnt
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(
        map((pays: IPays[]) =>
          this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value, this.editForm.get('nationalite')!.value)
        )
      )
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.natureConfigService
      .query()
      .pipe(map((res: HttpResponse<INatureConfig[]>) => res.body ?? []))
      .pipe(
        map((natureConfigs: INatureConfig[]) =>
          this.natureConfigService.addNatureConfigToCollectionIfMissing(natureConfigs, this.editForm.get('natureConfig')!.value)
        )
      )
      .subscribe((natureConfigs: INatureConfig[]) => (this.natureConfigsSharedCollection = natureConfigs));

    this.echlonService
      .query()
      .pipe(map((res: HttpResponse<IEchlon[]>) => res.body ?? []))
      .pipe(map((echlons: IEchlon[]) => this.echlonService.addEchlonToCollectionIfMissing(echlons, this.editForm.get('echlon')!.value)))
      .subscribe((echlons: IEchlon[]) => (this.echlonsSharedCollection = echlons));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.emploiService
      .query()
      .pipe(map((res: HttpResponse<IEmploi[]>) => res.body ?? []))
      .pipe(map((emplois: IEmploi[]) => this.emploiService.addEmploiToCollectionIfMissing(emplois, this.editForm.get('emploi')!.value)))
      .subscribe((emplois: IEmploi[]) => (this.emploisSharedCollection = emplois));

    this.typeHandicapService
      .query()
      .pipe(map((res: HttpResponse<ITypeHandicap[]>) => res.body ?? []))
      .pipe(
        map((typeHandicaps: ITypeHandicap[]) =>
          this.typeHandicapService.addTypeHandicapToCollectionIfMissing(typeHandicaps, this.editForm.get('typeHandicap')!.value)
        )
      )
      .subscribe((typeHandicaps: ITypeHandicap[]) => (this.typeHandicapsSharedCollection = typeHandicaps));

    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexe[]>) => res.body ?? []))
      .pipe(map((sexes: ISexe[]) => this.sexeService.addSexeToCollectionIfMissing(sexes, this.editForm.get('sexe')!.value)))
      .subscribe((sexes: ISexe[]) => (this.sexesSharedCollection = sexes));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('affilication')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing(typeContrats, this.editForm.get('typeContrat')!.value)
        )
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));

    this.sousTypeContratService
      .query()
      .pipe(map((res: HttpResponse<ISousTypeContrat[]>) => res.body ?? []))
      .pipe(
        map((sousTypeContrats: ISousTypeContrat[]) =>
          this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(sousTypeContrats, this.editForm.get('sousTypeContrat')!.value)
        )
      )
      .subscribe((sousTypeContrats: ISousTypeContrat[]) => (this.sousTypeContratsSharedCollection = sousTypeContrats));

    this.niveauEtudeService
      .query()
      .pipe(map((res: HttpResponse<INiveauEtude[]>) => res.body ?? []))
      .pipe(
        map((niveauEtudes: INiveauEtude[]) =>
          this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(niveauEtudes, this.editForm.get('niveauEtude')!.value)
        )
      )
      .subscribe((niveauEtudes: INiveauEtude[]) => (this.niveauEtudesSharedCollection = niveauEtudes));

    this.natureEligibiliteService
      .query()
      .pipe(map((res: HttpResponse<INatureEligibilite[]>) => res.body ?? []))
      .pipe(
        map((natureEligibilites: INatureEligibilite[]) =>
          this.natureEligibiliteService.addNatureEligibiliteToCollectionIfMissing(
            natureEligibilites,
            this.editForm.get('natureEligible')!.value
          )
        )
      )
      .subscribe((natureEligibilites: INatureEligibilite[]) => (this.natureEligibilitesSharedCollection = natureEligibilites));

    this.rebriqueService
      .query()
      .pipe(map((res: HttpResponse<IRebrique[]>) => res.body ?? []))
      .pipe(
        map((rebriques: IRebrique[]) =>
          this.rebriqueService.addRebriqueToCollectionIfMissing(rebriques, this.editForm.get('rebrique')!.value)
        )
      )
      .subscribe((rebriques: IRebrique[]) => (this.rebriquesSharedCollection = rebriques));

    this.natureAbsenceService
      .query()
      .pipe(map((res: HttpResponse<INatureAbsence[]>) => res.body ?? []))
      .pipe(
        map((natureAbsences: INatureAbsence[]) =>
          this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(natureAbsences, this.editForm.get('natureAbsence')!.value)
        )
      )
      .subscribe((natureAbsences: INatureAbsence[]) => (this.natureAbsencesSharedCollection = natureAbsences));

    this.plateService
      .query()
      .pipe(map((res: HttpResponse<IPlate[]>) => res.body ?? []))
      .pipe(map((plates: IPlate[]) => this.plateService.addPlateToCollectionIfMissing(plates, this.editForm.get('plate')!.value)))
      .subscribe((plates: IPlate[]) => (this.platesSharedCollection = plates));

    this.targetEligibleService
      .query()
      .pipe(map((res: HttpResponse<ITargetEligible[]>) => res.body ?? []))
      .pipe(
        map((targetEligibles: ITargetEligible[]) =>
          this.targetEligibleService.addTargetEligibleToCollectionIfMissing(targetEligibles, this.editForm.get('targetEnt')!.value)
        )
      )
      .subscribe((targetEligibles: ITargetEligible[]) => (this.targetEligiblesSharedCollection = targetEligibles));
  }

  protected createFromForm(): IEligibilite {
    return {
      ...new Eligibilite(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      nbEnt: this.editForm.get(['nbEnt'])!.value,
      ageEnt: this.editForm.get(['ageEnt'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
      valPayroll: this.editForm.get(['valPayroll'])!.value,
      nbDaysLeave: this.editForm.get(['nbDaysLeave'])!.value,
      pourValPayroll: this.editForm.get(['pourValPayroll'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      pays: this.editForm.get(['pays'])!.value,
      natureConfig: this.editForm.get(['natureConfig'])!.value,
      echlon: this.editForm.get(['echlon'])!.value,
      category: this.editForm.get(['category'])!.value,
      emploi: this.editForm.get(['emploi'])!.value,
      typeHandicap: this.editForm.get(['typeHandicap'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      typeContrat: this.editForm.get(['typeContrat'])!.value,
      sousTypeContrat: this.editForm.get(['sousTypeContrat'])!.value,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
      natureEligible: this.editForm.get(['natureEligible'])!.value,
      rebrique: this.editForm.get(['rebrique'])!.value,
      natureAbsence: this.editForm.get(['natureAbsence'])!.value,
      plate: this.editForm.get(['plate'])!.value,
      targetEnt: this.editForm.get(['targetEnt'])!.value,
    };
  }
}
