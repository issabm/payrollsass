import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPalierPlate, PalierPlate } from '../palier-plate.model';
import { PalierPlateService } from '../service/palier-plate.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IPlate } from 'app/entities/plate/plate.model';
import { PlateService } from 'app/entities/plate/service/plate.service';

@Component({
  selector: 'jhi-palier-plate-update',
  templateUrl: './palier-plate-update.component.html',
})
export class PalierPlateUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  platesSharedCollection: IPlate[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    annee: [],
    effectiValue: [],
    util: [],
    dateop: [],
    dateModif: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    pays: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    plateTarget: [],
    platBaseCalcul: [],
  });

  constructor(
    protected palierPlateService: PalierPlateService,
    protected paysService: PaysService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected plateService: PlateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierPlate }) => {
      if (palierPlate.id === undefined) {
        const today = dayjs().startOf('day');
        palierPlate.dateop = today;
        palierPlate.dateModif = today;
      }

      this.updateForm(palierPlate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const palierPlate = this.createFromForm();
    if (palierPlate.id !== undefined) {
      this.subscribeToSaveResponse(this.palierPlateService.update(palierPlate));
    } else {
      this.subscribeToSaveResponse(this.palierPlateService.create(palierPlate));
    }
  }

  trackPaysById(index: number, item: IPays): number {
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

  trackPlateById(index: number, item: IPlate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPalierPlate>>): void {
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

  protected updateForm(palierPlate: IPalierPlate): void {
    this.editForm.patchValue({
      id: palierPlate.id,
      code: palierPlate.code,
      libEn: palierPlate.libEn,
      libAr: palierPlate.libAr,
      annee: palierPlate.annee,
      effectiValue: palierPlate.effectiValue,
      util: palierPlate.util,
      dateop: palierPlate.dateop ? palierPlate.dateop.format(DATE_TIME_FORMAT) : null,
      dateModif: palierPlate.dateModif ? palierPlate.dateModif.format(DATE_TIME_FORMAT) : null,
      modifiedBy: palierPlate.modifiedBy,
      op: palierPlate.op,
      isDeleted: palierPlate.isDeleted,
      pays: palierPlate.pays,
      affilication: palierPlate.affilication,
      entreprise: palierPlate.entreprise,
      groupe: palierPlate.groupe,
      plateTarget: palierPlate.plateTarget,
      platBaseCalcul: palierPlate.platBaseCalcul,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, palierPlate.pays);
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      palierPlate.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      palierPlate.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, palierPlate.groupe);
    this.platesSharedCollection = this.plateService.addPlateToCollectionIfMissing(
      this.platesSharedCollection,
      palierPlate.plateTarget,
      palierPlate.platBaseCalcul
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

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

    this.plateService
      .query()
      .pipe(map((res: HttpResponse<IPlate[]>) => res.body ?? []))
      .pipe(
        map((plates: IPlate[]) =>
          this.plateService.addPlateToCollectionIfMissing(
            plates,
            this.editForm.get('plateTarget')!.value,
            this.editForm.get('platBaseCalcul')!.value
          )
        )
      )
      .subscribe((plates: IPlate[]) => (this.platesSharedCollection = plates));
  }

  protected createFromForm(): IPalierPlate {
    return {
      ...new PalierPlate(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      effectiValue: this.editForm.get(['effectiValue'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      dateModif: this.editForm.get(['dateModif'])!.value ? dayjs(this.editForm.get(['dateModif'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      plateTarget: this.editForm.get(['plateTarget'])!.value,
      platBaseCalcul: this.editForm.get(['platBaseCalcul'])!.value,
    };
  }
}
