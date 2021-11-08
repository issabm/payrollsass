import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITypeIdentite, TypeIdentite } from '../type-identite.model';
import { TypeIdentiteService } from '../service/type-identite.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';

@Component({
  selector: 'jhi-type-identite-update',
  templateUrl: './type-identite-update.component.html',
})
export class TypeIdentiteUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];

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
  });

  constructor(
    protected typeIdentiteService: TypeIdentiteService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeIdentite }) => {
      if (typeIdentite.id === undefined) {
        const today = dayjs().startOf('day');
        typeIdentite.dateop = today;
        typeIdentite.createdDate = today;
        typeIdentite.modifiedDate = today;
      }

      this.updateForm(typeIdentite);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeIdentite = this.createFromForm();
    if (typeIdentite.id !== undefined) {
      this.subscribeToSaveResponse(this.typeIdentiteService.update(typeIdentite));
    } else {
      this.subscribeToSaveResponse(this.typeIdentiteService.create(typeIdentite));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeIdentite>>): void {
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

  protected updateForm(typeIdentite: ITypeIdentite): void {
    this.editForm.patchValue({
      id: typeIdentite.id,
      code: typeIdentite.code,
      libAr: typeIdentite.libAr,
      libEn: typeIdentite.libEn,
      util: typeIdentite.util,
      dateop: typeIdentite.dateop ? typeIdentite.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: typeIdentite.modifiedBy,
      op: typeIdentite.op,
      isDeleted: typeIdentite.isDeleted,
      createdDate: typeIdentite.createdDate ? typeIdentite.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: typeIdentite.modifiedDate ? typeIdentite.modifiedDate.format(DATE_TIME_FORMAT) : null,
      pays: typeIdentite.pays,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, typeIdentite.pays);
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));
  }

  protected createFromForm(): ITypeIdentite {
    return {
      ...new TypeIdentite(),
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
    };
  }
}
