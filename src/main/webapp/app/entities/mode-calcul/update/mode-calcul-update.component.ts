import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IModeCalcul, ModeCalcul } from '../mode-calcul.model';
import { ModeCalculService } from '../service/mode-calcul.service';

@Component({
  selector: 'jhi-mode-calcul-update',
  templateUrl: './mode-calcul-update.component.html',
})
export class ModeCalculUpdateComponent implements OnInit {
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

  constructor(protected modeCalculService: ModeCalculService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeCalcul }) => {
      if (modeCalcul.id === undefined) {
        const today = dayjs().startOf('day');
        modeCalcul.dateop = today;
        modeCalcul.createdDate = today;
        modeCalcul.modifiedDate = today;
      }

      this.updateForm(modeCalcul);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modeCalcul = this.createFromForm();
    if (modeCalcul.id !== undefined) {
      this.subscribeToSaveResponse(this.modeCalculService.update(modeCalcul));
    } else {
      this.subscribeToSaveResponse(this.modeCalculService.create(modeCalcul));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeCalcul>>): void {
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

  protected updateForm(modeCalcul: IModeCalcul): void {
    this.editForm.patchValue({
      id: modeCalcul.id,
      code: modeCalcul.code,
      libAr: modeCalcul.libAr,
      libEn: modeCalcul.libEn,
      util: modeCalcul.util,
      dateop: modeCalcul.dateop ? modeCalcul.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: modeCalcul.modifiedBy,
      op: modeCalcul.op,
      isDeleted: modeCalcul.isDeleted,
      createdDate: modeCalcul.createdDate ? modeCalcul.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: modeCalcul.modifiedDate ? modeCalcul.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IModeCalcul {
    return {
      ...new ModeCalcul(),
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
