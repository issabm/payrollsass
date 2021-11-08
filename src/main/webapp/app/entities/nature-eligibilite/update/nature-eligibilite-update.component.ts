import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureEligibilite, NatureEligibilite } from '../nature-eligibilite.model';
import { NatureEligibiliteService } from '../service/nature-eligibilite.service';

@Component({
  selector: 'jhi-nature-eligibilite-update',
  templateUrl: './nature-eligibilite-update.component.html',
})
export class NatureEligibiliteUpdateComponent implements OnInit {
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
    protected natureEligibiliteService: NatureEligibiliteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureEligibilite }) => {
      if (natureEligibilite.id === undefined) {
        const today = dayjs().startOf('day');
        natureEligibilite.dateop = today;
        natureEligibilite.createdDate = today;
        natureEligibilite.modifiedDate = today;
      }

      this.updateForm(natureEligibilite);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureEligibilite = this.createFromForm();
    if (natureEligibilite.id !== undefined) {
      this.subscribeToSaveResponse(this.natureEligibiliteService.update(natureEligibilite));
    } else {
      this.subscribeToSaveResponse(this.natureEligibiliteService.create(natureEligibilite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureEligibilite>>): void {
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

  protected updateForm(natureEligibilite: INatureEligibilite): void {
    this.editForm.patchValue({
      id: natureEligibilite.id,
      code: natureEligibilite.code,
      libEn: natureEligibilite.libEn,
      libAr: natureEligibilite.libAr,
      libFr: natureEligibilite.libFr,
      dateop: natureEligibilite.dateop ? natureEligibilite.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: natureEligibilite.modifiedBy,
      createdBy: natureEligibilite.createdBy,
      op: natureEligibilite.op,
      util: natureEligibilite.util,
      isDeleted: natureEligibilite.isDeleted,
      createdDate: natureEligibilite.createdDate ? natureEligibilite.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureEligibilite.modifiedDate ? natureEligibilite.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INatureEligibilite {
    return {
      ...new NatureEligibilite(),
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
