import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISituation, Situation } from '../situation.model';
import { SituationService } from '../service/situation.service';

@Component({
  selector: 'jhi-situation-update',
  templateUrl: './situation-update.component.html',
})
export class SituationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    entityTarget: [],
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

  constructor(protected situationService: SituationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situation }) => {
      if (situation.id === undefined) {
        const today = dayjs().startOf('day');
        situation.dateop = today;
        situation.createdDate = today;
        situation.modifiedDate = today;
      }

      this.updateForm(situation);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situation = this.createFromForm();
    if (situation.id !== undefined) {
      this.subscribeToSaveResponse(this.situationService.update(situation));
    } else {
      this.subscribeToSaveResponse(this.situationService.create(situation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituation>>): void {
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

  protected updateForm(situation: ISituation): void {
    this.editForm.patchValue({
      id: situation.id,
      code: situation.code,
      entityTarget: situation.entityTarget,
      libAr: situation.libAr,
      libEn: situation.libEn,
      util: situation.util,
      dateop: situation.dateop ? situation.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: situation.modifiedBy,
      op: situation.op,
      isDeleted: situation.isDeleted,
      createdDate: situation.createdDate ? situation.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: situation.modifiedDate ? situation.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISituation {
    return {
      ...new Situation(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      entityTarget: this.editForm.get(['entityTarget'])!.value,
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
