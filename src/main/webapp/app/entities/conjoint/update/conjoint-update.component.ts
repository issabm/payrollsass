import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConjoint, Conjoint } from '../conjoint.model';
import { ConjointService } from '../service/conjoint.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

@Component({
  selector: 'jhi-conjoint-update',
  templateUrl: './conjoint-update.component.html',
})
export class ConjointUpdateComponent implements OnInit {
  isSaving = false;

  sexesSharedCollection: ISexe[] = [];
  paysSharedCollection: IPays[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [],
    nomAr: [],
    prenomAr: [],
    nomEn: [],
    prenomEn: [],
    dateNaiss: [],
    doesWork: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    sexe: [],
    nationalite: [],
  });

  constructor(
    protected conjointService: ConjointService,
    protected sexeService: SexeService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conjoint }) => {
      if (conjoint.id === undefined) {
        const today = dayjs().startOf('day');
        conjoint.dateop = today;
      }

      this.updateForm(conjoint);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conjoint = this.createFromForm();
    if (conjoint.id !== undefined) {
      this.subscribeToSaveResponse(this.conjointService.update(conjoint));
    } else {
      this.subscribeToSaveResponse(this.conjointService.create(conjoint));
    }
  }

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConjoint>>): void {
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

  protected updateForm(conjoint: IConjoint): void {
    this.editForm.patchValue({
      id: conjoint.id,
      matricule: conjoint.matricule,
      nomAr: conjoint.nomAr,
      prenomAr: conjoint.prenomAr,
      nomEn: conjoint.nomEn,
      prenomEn: conjoint.prenomEn,
      dateNaiss: conjoint.dateNaiss,
      doesWork: conjoint.doesWork,
      util: conjoint.util,
      dateop: conjoint.dateop ? conjoint.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: conjoint.modifiedBy,
      op: conjoint.op,
      isDeleted: conjoint.isDeleted,
      sexe: conjoint.sexe,
      nationalite: conjoint.nationalite,
    });

    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, conjoint.sexe);
    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, conjoint.nationalite);
  }

  protected loadRelationshipsOptions(): void {
    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexe[]>) => res.body ?? []))
      .pipe(map((sexes: ISexe[]) => this.sexeService.addSexeToCollectionIfMissing(sexes, this.editForm.get('sexe')!.value)))
      .subscribe((sexes: ISexe[]) => (this.sexesSharedCollection = sexes));

    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('nationalite')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));
  }

  protected createFromForm(): IConjoint {
    return {
      ...new Conjoint(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      nomAr: this.editForm.get(['nomAr'])!.value,
      prenomAr: this.editForm.get(['prenomAr'])!.value,
      nomEn: this.editForm.get(['nomEn'])!.value,
      prenomEn: this.editForm.get(['prenomEn'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value,
      doesWork: this.editForm.get(['doesWork'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
    };
  }
}
