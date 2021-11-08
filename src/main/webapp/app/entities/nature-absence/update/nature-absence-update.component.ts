import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureAbsence, NatureAbsence } from '../nature-absence.model';
import { NatureAbsenceService } from '../service/nature-absence.service';
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

@Component({
  selector: 'jhi-nature-absence-update',
  templateUrl: './nature-absence-update.component.html',
})
export class NatureAbsenceUpdateComponent implements OnInit {
  isSaving = false;

  natureConfigsSharedCollection: INatureConfig[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  sexesSharedCollection: ISexe[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    util: [],
    dateop: [],
    nbDays: [],
    valuePaied: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    natureConfig: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    sexe: [],
  });

  constructor(
    protected natureAbsenceService: NatureAbsenceService,
    protected natureConfigService: NatureConfigService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected sexeService: SexeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAbsence }) => {
      if (natureAbsence.id === undefined) {
        const today = dayjs().startOf('day');
        natureAbsence.dateop = today;
        natureAbsence.createdDate = today;
        natureAbsence.modifiedDate = today;
      }

      this.updateForm(natureAbsence);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureAbsence = this.createFromForm();
    if (natureAbsence.id !== undefined) {
      this.subscribeToSaveResponse(this.natureAbsenceService.update(natureAbsence));
    } else {
      this.subscribeToSaveResponse(this.natureAbsenceService.create(natureAbsence));
    }
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

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureAbsence>>): void {
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

  protected updateForm(natureAbsence: INatureAbsence): void {
    this.editForm.patchValue({
      id: natureAbsence.id,
      code: natureAbsence.code,
      libAr: natureAbsence.libAr,
      libEn: natureAbsence.libEn,
      util: natureAbsence.util,
      dateop: natureAbsence.dateop ? natureAbsence.dateop.format(DATE_TIME_FORMAT) : null,
      nbDays: natureAbsence.nbDays,
      valuePaied: natureAbsence.valuePaied,
      modifiedBy: natureAbsence.modifiedBy,
      op: natureAbsence.op,
      isDeleted: natureAbsence.isDeleted,
      createdDate: natureAbsence.createdDate ? natureAbsence.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureAbsence.modifiedDate ? natureAbsence.modifiedDate.format(DATE_TIME_FORMAT) : null,
      natureConfig: natureAbsence.natureConfig,
      affilication: natureAbsence.affilication,
      entreprise: natureAbsence.entreprise,
      groupe: natureAbsence.groupe,
      sexe: natureAbsence.sexe,
    });

    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      natureAbsence.natureConfig
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      natureAbsence.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      natureAbsence.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, natureAbsence.groupe);
    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, natureAbsence.sexe);
  }

  protected loadRelationshipsOptions(): void {
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

    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexe[]>) => res.body ?? []))
      .pipe(map((sexes: ISexe[]) => this.sexeService.addSexeToCollectionIfMissing(sexes, this.editForm.get('sexe')!.value)))
      .subscribe((sexes: ISexe[]) => (this.sexesSharedCollection = sexes));
  }

  protected createFromForm(): INatureAbsence {
    return {
      ...new NatureAbsence(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      nbDays: this.editForm.get(['nbDays'])!.value,
      valuePaied: this.editForm.get(['valuePaied'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      natureConfig: this.editForm.get(['natureConfig'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
    };
  }
}
