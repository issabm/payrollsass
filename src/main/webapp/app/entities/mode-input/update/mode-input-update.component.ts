import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IModeInput, ModeInput } from '../mode-input.model';
import { ModeInputService } from '../service/mode-input.service';

@Component({
  selector: 'jhi-mode-input-update',
  templateUrl: './mode-input-update.component.html',
})
export class ModeInputUpdateComponent implements OnInit {
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

  constructor(protected modeInputService: ModeInputService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeInput }) => {
      if (modeInput.id === undefined) {
        const today = dayjs().startOf('day');
        modeInput.dateop = today;
        modeInput.createdDate = today;
        modeInput.modifiedDate = today;
      }

      this.updateForm(modeInput);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modeInput = this.createFromForm();
    if (modeInput.id !== undefined) {
      this.subscribeToSaveResponse(this.modeInputService.update(modeInput));
    } else {
      this.subscribeToSaveResponse(this.modeInputService.create(modeInput));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeInput>>): void {
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

  protected updateForm(modeInput: IModeInput): void {
    this.editForm.patchValue({
      id: modeInput.id,
      code: modeInput.code,
      libAr: modeInput.libAr,
      libEn: modeInput.libEn,
      util: modeInput.util,
      dateop: modeInput.dateop ? modeInput.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: modeInput.modifiedBy,
      op: modeInput.op,
      isDeleted: modeInput.isDeleted,
      createdDate: modeInput.createdDate ? modeInput.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: modeInput.modifiedDate ? modeInput.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IModeInput {
    return {
      ...new ModeInput(),
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
