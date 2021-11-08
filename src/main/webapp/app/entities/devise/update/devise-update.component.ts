import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDevise, Devise } from '../devise.model';
import { DeviseService } from '../service/devise.service';

@Component({
  selector: 'jhi-devise-update',
  templateUrl: './devise-update.component.html',
})
export class DeviseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    dateop: [],
    util: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected deviseService: DeviseService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ devise }) => {
      if (devise.id === undefined) {
        const today = dayjs().startOf('day');
        devise.dateop = today;
        devise.createdDate = today;
        devise.modifiedDate = today;
      }

      this.updateForm(devise);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const devise = this.createFromForm();
    if (devise.id !== undefined) {
      this.subscribeToSaveResponse(this.deviseService.update(devise));
    } else {
      this.subscribeToSaveResponse(this.deviseService.create(devise));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevise>>): void {
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

  protected updateForm(devise: IDevise): void {
    this.editForm.patchValue({
      id: devise.id,
      code: devise.code,
      libAr: devise.libAr,
      libEn: devise.libEn,
      dateop: devise.dateop ? devise.dateop.format(DATE_TIME_FORMAT) : null,
      util: devise.util,
      modifiedBy: devise.modifiedBy,
      op: devise.op,
      isDeleted: devise.isDeleted,
      createdDate: devise.createdDate ? devise.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: devise.modifiedDate ? devise.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDevise {
    return {
      ...new Devise(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      util: this.editForm.get(['util'])!.value,
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
