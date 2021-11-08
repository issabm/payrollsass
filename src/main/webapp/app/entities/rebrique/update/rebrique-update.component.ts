import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRebrique, Rebrique } from '../rebrique.model';
import { RebriqueService } from '../service/rebrique.service';
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

@Component({
  selector: 'jhi-rebrique-update',
  templateUrl: './rebrique-update.component.html',
})
export class RebriqueUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  natureConfigsSharedCollection: INatureConfig[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  modeInputsSharedCollection: IModeInput[] = [];
  sensSharedCollection: ISens[] = [];
  concernesSharedCollection: IConcerne[] = [];
  frequencesSharedCollection: IFrequence[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    code: [],
    libAr: [],
    libFr: [],
    libEn: [],
    inTax: [],
    minValue: [],
    maxValue: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    util: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    pays: [],
    natureConfig: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    modeInput: [],
    sens: [],
    concerne: [],
    frequence: [],
  });

  constructor(
    protected rebriqueService: RebriqueService,
    protected paysService: PaysService,
    protected natureConfigService: NatureConfigService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected modeInputService: ModeInputService,
    protected sensService: SensService,
    protected concerneService: ConcerneService,
    protected frequenceService: FrequenceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rebrique }) => {
      if (rebrique.id === undefined) {
        const today = dayjs().startOf('day');
        rebrique.dateop = today;
        rebrique.createdDate = today;
        rebrique.modifiedDate = today;
      }

      this.updateForm(rebrique);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rebrique = this.createFromForm();
    if (rebrique.id !== undefined) {
      this.subscribeToSaveResponse(this.rebriqueService.update(rebrique));
    } else {
      this.subscribeToSaveResponse(this.rebriqueService.create(rebrique));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackNatureConfigById(index: number, item: INatureConfig): number {
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

  trackModeInputById(index: number, item: IModeInput): number {
    return item.id!;
  }

  trackSensById(index: number, item: ISens): number {
    return item.id!;
  }

  trackConcerneById(index: number, item: IConcerne): number {
    return item.id!;
  }

  trackFrequenceById(index: number, item: IFrequence): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRebrique>>): void {
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

  protected updateForm(rebrique: IRebrique): void {
    this.editForm.patchValue({
      id: rebrique.id,
      priorite: rebrique.priorite,
      code: rebrique.code,
      libAr: rebrique.libAr,
      libFr: rebrique.libFr,
      libEn: rebrique.libEn,
      inTax: rebrique.inTax,
      minValue: rebrique.minValue,
      maxValue: rebrique.maxValue,
      dateSituation: rebrique.dateSituation,
      dateop: rebrique.dateop ? rebrique.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: rebrique.modifiedBy,
      createdBy: rebrique.createdBy,
      util: rebrique.util,
      op: rebrique.op,
      isDeleted: rebrique.isDeleted,
      createdDate: rebrique.createdDate ? rebrique.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: rebrique.modifiedDate ? rebrique.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: rebrique.pays,
      natureConfig: rebrique.natureConfig,
      affilication: rebrique.affilication,
      entreprise: rebrique.entreprise,
      groupe: rebrique.groupe,
      modeInput: rebrique.modeInput,
      sens: rebrique.sens,
      concerne: rebrique.concerne,
      frequence: rebrique.frequence,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, rebrique.pays);
    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      rebrique.natureConfig
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      rebrique.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      rebrique.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, rebrique.groupe);
    this.modeInputsSharedCollection = this.modeInputService.addModeInputToCollectionIfMissing(
      this.modeInputsSharedCollection,
      rebrique.modeInput
    );
    this.sensSharedCollection = this.sensService.addSensToCollectionIfMissing(this.sensSharedCollection, rebrique.sens);
    this.concernesSharedCollection = this.concerneService.addConcerneToCollectionIfMissing(
      this.concernesSharedCollection,
      rebrique.concerne
    );
    this.frequencesSharedCollection = this.frequenceService.addFrequenceToCollectionIfMissing(
      this.frequencesSharedCollection,
      rebrique.frequence
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
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

    this.modeInputService
      .query()
      .pipe(map((res: HttpResponse<IModeInput[]>) => res.body ?? []))
      .pipe(
        map((modeInputs: IModeInput[]) =>
          this.modeInputService.addModeInputToCollectionIfMissing(modeInputs, this.editForm.get('modeInput')!.value)
        )
      )
      .subscribe((modeInputs: IModeInput[]) => (this.modeInputsSharedCollection = modeInputs));

    this.sensService
      .query()
      .pipe(map((res: HttpResponse<ISens[]>) => res.body ?? []))
      .pipe(map((sens: ISens[]) => this.sensService.addSensToCollectionIfMissing(sens, this.editForm.get('sens')!.value)))
      .subscribe((sens: ISens[]) => (this.sensSharedCollection = sens));

    this.concerneService
      .query()
      .pipe(map((res: HttpResponse<IConcerne[]>) => res.body ?? []))
      .pipe(
        map((concernes: IConcerne[]) =>
          this.concerneService.addConcerneToCollectionIfMissing(concernes, this.editForm.get('concerne')!.value)
        )
      )
      .subscribe((concernes: IConcerne[]) => (this.concernesSharedCollection = concernes));

    this.frequenceService
      .query()
      .pipe(map((res: HttpResponse<IFrequence[]>) => res.body ?? []))
      .pipe(
        map((frequences: IFrequence[]) =>
          this.frequenceService.addFrequenceToCollectionIfMissing(frequences, this.editForm.get('frequence')!.value)
        )
      )
      .subscribe((frequences: IFrequence[]) => (this.frequencesSharedCollection = frequences));
  }

  protected createFromForm(): IRebrique {
    return {
      ...new Rebrique(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      inTax: this.editForm.get(['inTax'])!.value,
      minValue: this.editForm.get(['minValue'])!.value,
      maxValue: this.editForm.get(['maxValue'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      util: this.editForm.get(['util'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      pays: this.editForm.get(['pays'])!.value,
      natureConfig: this.editForm.get(['natureConfig'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      modeInput: this.editForm.get(['modeInput'])!.value,
      sens: this.editForm.get(['sens'])!.value,
      concerne: this.editForm.get(['concerne'])!.value,
      frequence: this.editForm.get(['frequence'])!.value,
    };
  }
}
