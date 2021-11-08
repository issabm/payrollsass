import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPlate, Plate } from '../plate.model';
import { PlateService } from '../service/plate.service';
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

@Component({
  selector: 'jhi-plate-update',
  templateUrl: './plate-update.component.html',
})
export class PlateUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  natureConfigsSharedCollection: INatureConfig[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  modeCalculsSharedCollection: IModeCalcul[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    valueCalcul: [],
    code: [],
    libAr: [],
    libEn: [],
    dateop: [],
    util: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    pays: [],
    natureConfig: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    modeCalcul: [],
  });

  constructor(
    protected plateService: PlateService,
    protected paysService: PaysService,
    protected natureConfigService: NatureConfigService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected modeCalculService: ModeCalculService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plate }) => {
      if (plate.id === undefined) {
        const today = dayjs().startOf('day');
        plate.dateop = today;
        plate.createdDate = today;
        plate.modifiedDate = today;
      }

      this.updateForm(plate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plate = this.createFromForm();
    if (plate.id !== undefined) {
      this.subscribeToSaveResponse(this.plateService.update(plate));
    } else {
      this.subscribeToSaveResponse(this.plateService.create(plate));
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

  trackModeCalculById(index: number, item: IModeCalcul): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlate>>): void {
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

  protected updateForm(plate: IPlate): void {
    this.editForm.patchValue({
      id: plate.id,
      priorite: plate.priorite,
      valueCalcul: plate.valueCalcul,
      code: plate.code,
      libAr: plate.libAr,
      libEn: plate.libEn,
      dateop: plate.dateop ? plate.dateop.format(DATE_TIME_FORMAT) : null,
      util: plate.util,
      modifiedBy: plate.modifiedBy,
      op: plate.op,
      isDeleted: plate.isDeleted,
      createdDate: plate.createdDate ? plate.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: plate.modifiedDate ? plate.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: plate.pays,
      natureConfig: plate.natureConfig,
      affilication: plate.affilication,
      entreprise: plate.entreprise,
      groupe: plate.groupe,
      modeCalcul: plate.modeCalcul,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, plate.pays);
    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      plate.natureConfig
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      plate.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      plate.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, plate.groupe);
    this.modeCalculsSharedCollection = this.modeCalculService.addModeCalculToCollectionIfMissing(
      this.modeCalculsSharedCollection,
      plate.modeCalcul
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

    this.modeCalculService
      .query()
      .pipe(map((res: HttpResponse<IModeCalcul[]>) => res.body ?? []))
      .pipe(
        map((modeCalculs: IModeCalcul[]) =>
          this.modeCalculService.addModeCalculToCollectionIfMissing(modeCalculs, this.editForm.get('modeCalcul')!.value)
        )
      )
      .subscribe((modeCalculs: IModeCalcul[]) => (this.modeCalculsSharedCollection = modeCalculs));
  }

  protected createFromForm(): IPlate {
    return {
      ...new Plate(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      valueCalcul: this.editForm.get(['valueCalcul'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      util: this.editForm.get(['util'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
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
      modeCalcul: this.editForm.get(['modeCalcul'])!.value,
    };
  }
}
