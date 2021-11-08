import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITypeHandicap, TypeHandicap } from '../type-handicap.model';
import { TypeHandicapService } from '../service/type-handicap.service';

@Component({
  selector: 'jhi-type-handicap-update',
  templateUrl: './type-handicap-update.component.html',
})
export class TypeHandicapUpdateComponent implements OnInit {
  isSaving = false;

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
  });

  constructor(protected typeHandicapService: TypeHandicapService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeHandicap }) => {
      if (typeHandicap.id === undefined) {
        const today = dayjs().startOf('day');
        typeHandicap.dateop = today;
        typeHandicap.createdDate = today;
        typeHandicap.modifiedDate = today;
      }

      this.updateForm(typeHandicap);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeHandicap = this.createFromForm();
    if (typeHandicap.id !== undefined) {
      this.subscribeToSaveResponse(this.typeHandicapService.update(typeHandicap));
    } else {
      this.subscribeToSaveResponse(this.typeHandicapService.create(typeHandicap));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeHandicap>>): void {
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

  protected updateForm(typeHandicap: ITypeHandicap): void {
    this.editForm.patchValue({
      id: typeHandicap.id,
      code: typeHandicap.code,
      libAr: typeHandicap.libAr,
      libEn: typeHandicap.libEn,
      util: typeHandicap.util,
      dateop: typeHandicap.dateop ? typeHandicap.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: typeHandicap.modifiedBy,
      op: typeHandicap.op,
      isDeleted: typeHandicap.isDeleted,
      createdDate: typeHandicap.createdDate ? typeHandicap.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: typeHandicap.modifiedDate ? typeHandicap.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITypeHandicap {
    return {
      ...new TypeHandicap(),
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
    };
  }
}
