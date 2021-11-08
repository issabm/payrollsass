import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEnfant, Enfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { TypeHandicapService } from 'app/entities/type-handicap/service/type-handicap.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { INiveauScolaire } from 'app/entities/niveau-scolaire/niveau-scolaire.model';
import { NiveauScolaireService } from 'app/entities/niveau-scolaire/service/niveau-scolaire.service';
import { IFamille } from 'app/entities/famille/famille.model';
import { FamilleService } from 'app/entities/famille/service/famille.service';

@Component({
  selector: 'jhi-enfant-update',
  templateUrl: './enfant-update.component.html',
})
export class EnfantUpdateComponent implements OnInit {
  isSaving = false;

  typeHandicapsSharedCollection: ITypeHandicap[] = [];
  sexesSharedCollection: ISexe[] = [];
  niveauScolairesSharedCollection: INiveauScolaire[] = [];
  famillesSharedCollection: IFamille[] = [];

  editForm = this.fb.group({
    id: [],
    nomAr: [],
    prenomAr: [],
    nomEn: [],
    prenomEn: [],
    dateNaiss: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    typeHandicap: [],
    sexe: [],
    nivScolaire: [],
    famille: [],
  });

  constructor(
    protected enfantService: EnfantService,
    protected typeHandicapService: TypeHandicapService,
    protected sexeService: SexeService,
    protected niveauScolaireService: NiveauScolaireService,
    protected familleService: FamilleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enfant }) => {
      if (enfant.id === undefined) {
        const today = dayjs().startOf('day');
        enfant.dateop = today;
      }

      this.updateForm(enfant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enfant = this.createFromForm();
    if (enfant.id !== undefined) {
      this.subscribeToSaveResponse(this.enfantService.update(enfant));
    } else {
      this.subscribeToSaveResponse(this.enfantService.create(enfant));
    }
  }

  trackTypeHandicapById(index: number, item: ITypeHandicap): number {
    return item.id!;
  }

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  trackNiveauScolaireById(index: number, item: INiveauScolaire): number {
    return item.id!;
  }

  trackFamilleById(index: number, item: IFamille): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnfant>>): void {
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

  protected updateForm(enfant: IEnfant): void {
    this.editForm.patchValue({
      id: enfant.id,
      nomAr: enfant.nomAr,
      prenomAr: enfant.prenomAr,
      nomEn: enfant.nomEn,
      prenomEn: enfant.prenomEn,
      dateNaiss: enfant.dateNaiss,
      util: enfant.util,
      dateop: enfant.dateop ? enfant.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: enfant.modifiedBy,
      op: enfant.op,
      isDeleted: enfant.isDeleted,
      typeHandicap: enfant.typeHandicap,
      sexe: enfant.sexe,
      nivScolaire: enfant.nivScolaire,
      famille: enfant.famille,
    });

    this.typeHandicapsSharedCollection = this.typeHandicapService.addTypeHandicapToCollectionIfMissing(
      this.typeHandicapsSharedCollection,
      enfant.typeHandicap
    );
    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, enfant.sexe);
    this.niveauScolairesSharedCollection = this.niveauScolaireService.addNiveauScolaireToCollectionIfMissing(
      this.niveauScolairesSharedCollection,
      enfant.nivScolaire
    );
    this.famillesSharedCollection = this.familleService.addFamilleToCollectionIfMissing(this.famillesSharedCollection, enfant.famille);
  }

  protected loadRelationshipsOptions(): void {
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

    this.niveauScolaireService
      .query()
      .pipe(map((res: HttpResponse<INiveauScolaire[]>) => res.body ?? []))
      .pipe(
        map((niveauScolaires: INiveauScolaire[]) =>
          this.niveauScolaireService.addNiveauScolaireToCollectionIfMissing(niveauScolaires, this.editForm.get('nivScolaire')!.value)
        )
      )
      .subscribe((niveauScolaires: INiveauScolaire[]) => (this.niveauScolairesSharedCollection = niveauScolaires));

    this.familleService
      .query()
      .pipe(map((res: HttpResponse<IFamille[]>) => res.body ?? []))
      .pipe(
        map((familles: IFamille[]) => this.familleService.addFamilleToCollectionIfMissing(familles, this.editForm.get('famille')!.value))
      )
      .subscribe((familles: IFamille[]) => (this.famillesSharedCollection = familles));
  }

  protected createFromForm(): IEnfant {
    return {
      ...new Enfant(),
      id: this.editForm.get(['id'])!.value,
      nomAr: this.editForm.get(['nomAr'])!.value,
      prenomAr: this.editForm.get(['prenomAr'])!.value,
      nomEn: this.editForm.get(['nomEn'])!.value,
      prenomEn: this.editForm.get(['prenomEn'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      typeHandicap: this.editForm.get(['typeHandicap'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      nivScolaire: this.editForm.get(['nivScolaire'])!.value,
      famille: this.editForm.get(['famille'])!.value,
    };
  }
}
