import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAffiliation, Affiliation } from '../affiliation.model';
import { AffiliationService } from '../service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

@Component({
  selector: 'jhi-affiliation-update',
  templateUrl: './affiliation-update.component.html',
})
export class AffiliationUpdateComponent implements OnInit {
  isSaving = false;

  entreprisesSharedCollection: IEntreprise[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  paysSharedCollection: IPays[] = [];
  devisesSharedCollection: IDevise[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    tel: [],
    email: [],
    fax: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    entreprise: [],
    direction: [],
    pays: [],
    devise: [],
  });

  constructor(
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected paysService: PaysService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliation }) => {
      if (affiliation.id === undefined) {
        const today = dayjs().startOf('day');
        affiliation.dateop = today;
      }

      this.updateForm(affiliation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affiliation = this.createFromForm();
    if (affiliation.id !== undefined) {
      this.subscribeToSaveResponse(this.affiliationService.update(affiliation));
    } else {
      this.subscribeToSaveResponse(this.affiliationService.create(affiliation));
    }
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackDeviseById(index: number, item: IDevise): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliation>>): void {
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

  protected updateForm(affiliation: IAffiliation): void {
    this.editForm.patchValue({
      id: affiliation.id,
      code: affiliation.code,
      libAr: affiliation.libAr,
      libEn: affiliation.libEn,
      tel: affiliation.tel,
      email: affiliation.email,
      fax: affiliation.fax,
      util: affiliation.util,
      dateop: affiliation.dateop ? affiliation.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: affiliation.modifiedBy,
      op: affiliation.op,
      isDeleted: affiliation.isDeleted,
      entreprise: affiliation.entreprise,
      direction: affiliation.direction,
      pays: affiliation.pays,
      devise: affiliation.devise,
    });

    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      affiliation.entreprise
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      affiliation.direction
    );
    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, affiliation.pays);
    this.devisesSharedCollection = this.deviseService.addDeviseToCollectionIfMissing(this.devisesSharedCollection, affiliation.devise);
  }

  protected loadRelationshipsOptions(): void {
    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('direction')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));

    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.deviseService
      .query()
      .pipe(map((res: HttpResponse<IDevise[]>) => res.body ?? []))
      .pipe(map((devises: IDevise[]) => this.deviseService.addDeviseToCollectionIfMissing(devises, this.editForm.get('devise')!.value)))
      .subscribe((devises: IDevise[]) => (this.devisesSharedCollection = devises));
  }

  protected createFromForm(): IAffiliation {
    return {
      ...new Affiliation(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      email: this.editForm.get(['email'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      direction: this.editForm.get(['direction'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      devise: this.editForm.get(['devise'])!.value,
    };
  }
}
