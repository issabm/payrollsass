import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INiveauEtude, NiveauEtude } from '../niveau-etude.model';
import { NiveauEtudeService } from '../service/niveau-etude.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

@Component({
  selector: 'jhi-niveau-etude-update',
  templateUrl: './niveau-etude-update.component.html',
})
export class NiveauEtudeUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];

  editForm = this.fb.group({
    id: [],
    orderLevel: [],
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
  });

  constructor(
    protected niveauEtudeService: NiveauEtudeService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveauEtude }) => {
      if (niveauEtude.id === undefined) {
        const today = dayjs().startOf('day');
        niveauEtude.dateop = today;
        niveauEtude.createdDate = today;
        niveauEtude.modifiedDate = today;
      }

      this.updateForm(niveauEtude);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const niveauEtude = this.createFromForm();
    if (niveauEtude.id !== undefined) {
      this.subscribeToSaveResponse(this.niveauEtudeService.update(niveauEtude));
    } else {
      this.subscribeToSaveResponse(this.niveauEtudeService.create(niveauEtude));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INiveauEtude>>): void {
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

  protected updateForm(niveauEtude: INiveauEtude): void {
    this.editForm.patchValue({
      id: niveauEtude.id,
      orderLevel: niveauEtude.orderLevel,
      code: niveauEtude.code,
      libAr: niveauEtude.libAr,
      libEn: niveauEtude.libEn,
      util: niveauEtude.util,
      dateop: niveauEtude.dateop ? niveauEtude.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: niveauEtude.modifiedBy,
      op: niveauEtude.op,
      isDeleted: niveauEtude.isDeleted,
      createdDate: niveauEtude.createdDate ? niveauEtude.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: niveauEtude.modifiedDate ? niveauEtude.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: niveauEtude.pays,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, niveauEtude.pays);
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));
  }

  protected createFromForm(): INiveauEtude {
    return {
      ...new NiveauEtude(),
      id: this.editForm.get(['id'])!.value,
      orderLevel: this.editForm.get(['orderLevel'])!.value,
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
    };
  }
}
