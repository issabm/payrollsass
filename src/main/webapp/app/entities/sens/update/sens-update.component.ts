import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISens, Sens } from '../sens.model';
import { SensService } from '../service/sens.service';

@Component({
  selector: 'jhi-sens-update',
  templateUrl: './sens-update.component.html',
})
export class SensUpdateComponent implements OnInit {
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

  constructor(protected sensService: SensService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sens }) => {
      if (sens.id === undefined) {
        const today = dayjs().startOf('day');
        sens.dateop = today;
        sens.createdDate = today;
        sens.modifiedDate = today;
      }

      this.updateForm(sens);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sens = this.createFromForm();
    if (sens.id !== undefined) {
      this.subscribeToSaveResponse(this.sensService.update(sens));
    } else {
      this.subscribeToSaveResponse(this.sensService.create(sens));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISens>>): void {
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

  protected updateForm(sens: ISens): void {
    this.editForm.patchValue({
      id: sens.id,
      code: sens.code,
      libAr: sens.libAr,
      libEn: sens.libEn,
      util: sens.util,
      dateop: sens.dateop ? sens.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: sens.modifiedBy,
      op: sens.op,
      isDeleted: sens.isDeleted,
      createdDate: sens.createdDate ? sens.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: sens.modifiedDate ? sens.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISens {
    return {
      ...new Sens(),
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
