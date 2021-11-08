import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFrequence, Frequence } from '../frequence.model';
import { FrequenceService } from '../service/frequence.service';

@Component({
  selector: 'jhi-frequence-update',
  templateUrl: './frequence-update.component.html',
})
export class FrequenceUpdateComponent implements OnInit {
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

  constructor(protected frequenceService: FrequenceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ frequence }) => {
      if (frequence.id === undefined) {
        const today = dayjs().startOf('day');
        frequence.dateop = today;
        frequence.createdDate = today;
        frequence.modifiedDate = today;
      }

      this.updateForm(frequence);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const frequence = this.createFromForm();
    if (frequence.id !== undefined) {
      this.subscribeToSaveResponse(this.frequenceService.update(frequence));
    } else {
      this.subscribeToSaveResponse(this.frequenceService.create(frequence));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFrequence>>): void {
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

  protected updateForm(frequence: IFrequence): void {
    this.editForm.patchValue({
      id: frequence.id,
      code: frequence.code,
      libAr: frequence.libAr,
      libEn: frequence.libEn,
      util: frequence.util,
      dateop: frequence.dateop ? frequence.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: frequence.modifiedBy,
      op: frequence.op,
      isDeleted: frequence.isDeleted,
      createdDate: frequence.createdDate ? frequence.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: frequence.modifiedDate ? frequence.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IFrequence {
    return {
      ...new Frequence(),
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
