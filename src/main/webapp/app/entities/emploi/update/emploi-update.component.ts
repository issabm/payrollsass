import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEmploi, Emploi } from '../emploi.model';
import { EmploiService } from '../service/emploi.service';
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

@Component({
  selector: 'jhi-emploi-update',
  templateUrl: './emploi-update.component.html',
})
export class EmploiUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  natureConfigsSharedCollection: INatureConfig[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    util: [],
    dateop: [],
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
  });

  constructor(
    protected emploiService: EmploiService,
    protected paysService: PaysService,
    protected natureConfigService: NatureConfigService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emploi }) => {
      if (emploi.id === undefined) {
        const today = dayjs().startOf('day');
        emploi.dateop = today;
        emploi.createdDate = today;
        emploi.modifiedDate = today;
      }

      this.updateForm(emploi);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emploi = this.createFromForm();
    if (emploi.id !== undefined) {
      this.subscribeToSaveResponse(this.emploiService.update(emploi));
    } else {
      this.subscribeToSaveResponse(this.emploiService.create(emploi));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploi>>): void {
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

  protected updateForm(emploi: IEmploi): void {
    this.editForm.patchValue({
      id: emploi.id,
      code: emploi.code,
      libAr: emploi.libAr,
      libEn: emploi.libEn,
      util: emploi.util,
      dateop: emploi.dateop ? emploi.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: emploi.modifiedBy,
      op: emploi.op,
      isDeleted: emploi.isDeleted,
      createdDate: emploi.createdDate ? emploi.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: emploi.modifiedDate ? emploi.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: emploi.pays,
      natureConfig: emploi.natureConfig,
      affilication: emploi.affilication,
      entreprise: emploi.entreprise,
      groupe: emploi.groupe,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, emploi.pays);
    this.natureConfigsSharedCollection = this.natureConfigService.addNatureConfigToCollectionIfMissing(
      this.natureConfigsSharedCollection,
      emploi.natureConfig
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      emploi.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      emploi.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, emploi.groupe);
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
  }

  protected createFromForm(): IEmploi {
    return {
      ...new Emploi(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
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
    };
  }
}
