import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEligibiliteExclude, EligibiliteExclude } from '../eligibilite-exclude.model';
import { EligibiliteExcludeService } from '../service/eligibilite-exclude.service';

@Component({
  selector: 'jhi-eligibilite-exclude-update',
  templateUrl: './eligibilite-exclude-update.component.html',
})
export class EligibiliteExcludeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    anneeBegin: [],
    moisBegin: [],
    anneeEnd: [],
    moisEnd: [],
    matricule: [],
    code: [],
    libEn: [],
    libAr: [],
    libFr: [],
    annee: [],
    valPayroll: [],
    nbDaysLeave: [],
    pourValPayroll: [],
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
    protected eligibiliteExcludeService: EligibiliteExcludeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eligibiliteExclude }) => {
      if (eligibiliteExclude.id === undefined) {
        const today = dayjs().startOf('day');
        eligibiliteExclude.dateop = today;
        eligibiliteExclude.createdDate = today;
        eligibiliteExclude.modifiedDate = today;
      }

      this.updateForm(eligibiliteExclude);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eligibiliteExclude = this.createFromForm();
    if (eligibiliteExclude.id !== undefined) {
      this.subscribeToSaveResponse(this.eligibiliteExcludeService.update(eligibiliteExclude));
    } else {
      this.subscribeToSaveResponse(this.eligibiliteExcludeService.create(eligibiliteExclude));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEligibiliteExclude>>): void {
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

  protected updateForm(eligibiliteExclude: IEligibiliteExclude): void {
    this.editForm.patchValue({
      id: eligibiliteExclude.id,
      anneeBegin: eligibiliteExclude.anneeBegin,
      moisBegin: eligibiliteExclude.moisBegin,
      anneeEnd: eligibiliteExclude.anneeEnd,
      moisEnd: eligibiliteExclude.moisEnd,
      matricule: eligibiliteExclude.matricule,
      code: eligibiliteExclude.code,
      libEn: eligibiliteExclude.libEn,
      libAr: eligibiliteExclude.libAr,
      libFr: eligibiliteExclude.libFr,
      annee: eligibiliteExclude.annee,
      valPayroll: eligibiliteExclude.valPayroll,
      nbDaysLeave: eligibiliteExclude.nbDaysLeave,
      pourValPayroll: eligibiliteExclude.pourValPayroll,
      dateop: eligibiliteExclude.dateop ? eligibiliteExclude.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: eligibiliteExclude.modifiedBy,
      createdBy: eligibiliteExclude.createdBy,
      op: eligibiliteExclude.op,
      util: eligibiliteExclude.util,
      isDeleted: eligibiliteExclude.isDeleted,
      createdDate: eligibiliteExclude.createdDate ? eligibiliteExclude.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: eligibiliteExclude.modifiedDate ? eligibiliteExclude.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IEligibiliteExclude {
    return {
      ...new EligibiliteExclude(),
      id: this.editForm.get(['id'])!.value,
      anneeBegin: this.editForm.get(['anneeBegin'])!.value,
      moisBegin: this.editForm.get(['moisBegin'])!.value,
      anneeEnd: this.editForm.get(['anneeEnd'])!.value,
      moisEnd: this.editForm.get(['moisEnd'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      valPayroll: this.editForm.get(['valPayroll'])!.value,
      nbDaysLeave: this.editForm.get(['nbDaysLeave'])!.value,
      pourValPayroll: this.editForm.get(['pourValPayroll'])!.value,
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
