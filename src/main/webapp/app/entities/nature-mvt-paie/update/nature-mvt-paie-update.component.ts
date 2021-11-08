import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureMvtPaie, NatureMvtPaie } from '../nature-mvt-paie.model';
import { NatureMvtPaieService } from '../service/nature-mvt-paie.service';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { NatureConfigService } from 'app/entities/nature-config/service/nature-config.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';

@Component({
  selector: 'jhi-nature-mvt-paie-update',
  templateUrl: './nature-mvt-paie-update.component.html',
})
export class NatureMvtPaieUpdateComponent implements OnInit {
  isSaving = false;

  natureConfigsSharedCollection: INatureConfig[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    libFr: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    natureConfig: [],
    affilication: [],
    entreprise: [],
    groupe: [],
  });

  constructor(
    protected natureMvtPaieService: NatureMvtPaieService,
    protected natureConfigService: NatureConfigService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureMvtPaie }) => {
      if (natureMvtPaie.id === undefined) {
        const today = dayjs().startOf('day');
        natureMvtPaie.dateop = today;
        natureMvtPaie.createdDate = today;
        natureMvtPaie.modifiedDate = today;
      }

      this.updateForm(natureMvtPaie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureMvtPaie = this.createFromForm();
    if (natureMvtPaie.id !== undefined) {
      this.subscribeToSaveResponse(this.natureMvtPaieService.update(natureMvtPaie));
    } else {
      this.subscribeToSaveResponse(this.natureMvtPaieService.create(natureMvtPaie));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureMvtPaie>>): void {
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

  protected updateForm(natureMvtPaie: INatureMvtPaie): void {
    this.editForm.patchValue({
      id: natureMvtPaie.id,
      code: natureMvtPaie.code,
      libEn: natureMvtPaie.libEn,
      libAr: natureMvtPaie.libAr,
      libFr: natureMvtPaie.libFr,
      dateop: natureMvtPaie.dateop ? natureMvtPaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: natureMvtPaie.modifiedBy,
      createdBy: natureMvtPaie.createdBy,
      op: natureMvtPaie.op,
      util: natureMvtPaie.util,
      isDeleted: natureMvtPaie.isDeleted,
      createdDate: natureMvtPaie.createdDate ? natureMvtPaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureMvtPaie.modifiedDate ? natureMvtPaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
      natureConfig: natureMvtPaie.natureConfig,
      affilication: natureMvtPaie.affilication,
      entreprise: natureMvtPaie.entreprise,
      groupe: natureMvtPaie.groupe,
    });

    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      natureMvtPaie.natureConfig
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      natureMvtPaie.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      natureMvtPaie.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, natureMvtPaie.groupe);
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
  }

  protected createFromForm(): INatureMvtPaie {
    return {
      ...new NatureMvtPaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
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
      natureConfig: this.editForm.get(['natureConfig'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
    };
  }
}
