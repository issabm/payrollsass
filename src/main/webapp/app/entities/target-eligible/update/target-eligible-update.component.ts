import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITargetEligible, TargetEligible } from '../target-eligible.model';
import { TargetEligibleService } from '../service/target-eligible.service';

@Component({
  selector: 'jhi-target-eligible-update',
  templateUrl: './target-eligible-update.component.html',
})
export class TargetEligibleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    libFr: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(
    protected targetEligibleService: TargetEligibleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ targetEligible }) => {
      if (targetEligible.id === undefined) {
        const today = dayjs().startOf('day');
        targetEligible.dateop = today;
        targetEligible.createdDate = today;
        targetEligible.modifiedDate = today;
      }

      this.updateForm(targetEligible);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const targetEligible = this.createFromForm();
    if (targetEligible.id !== undefined) {
      this.subscribeToSaveResponse(this.targetEligibleService.update(targetEligible));
    } else {
      this.subscribeToSaveResponse(this.targetEligibleService.create(targetEligible));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITargetEligible>>): void {
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

  protected updateForm(targetEligible: ITargetEligible): void {
    this.editForm.patchValue({
      id: targetEligible.id,
      code: targetEligible.code,
      libEn: targetEligible.libEn,
      libAr: targetEligible.libAr,
      libFr: targetEligible.libFr,
      dateop: targetEligible.dateop ? targetEligible.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: targetEligible.modifiedBy,
      createdBy: targetEligible.createdBy,
      op: targetEligible.op,
      util: targetEligible.util,
      isDeleted: targetEligible.isDeleted,
      createdDate: targetEligible.createdDate ? targetEligible.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: targetEligible.modifiedDate ? targetEligible.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITargetEligible {
    return {
      ...new TargetEligible(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
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
