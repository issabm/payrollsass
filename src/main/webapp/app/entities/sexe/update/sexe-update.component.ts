import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISexe, Sexe } from '../sexe.model';
import { SexeService } from '../service/sexe.service';

@Component({
  selector: 'jhi-sexe-update',
  templateUrl: './sexe-update.component.html',
})
export class SexeUpdateComponent implements OnInit {
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

  constructor(protected sexeService: SexeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => {
      if (sexe.id === undefined) {
        const today = dayjs().startOf('day');
        sexe.dateop = today;
        sexe.createdDate = today;
        sexe.modifiedDate = today;
      }

      this.updateForm(sexe);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sexe = this.createFromForm();
    if (sexe.id !== undefined) {
      this.subscribeToSaveResponse(this.sexeService.update(sexe));
    } else {
      this.subscribeToSaveResponse(this.sexeService.create(sexe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISexe>>): void {
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

  protected updateForm(sexe: ISexe): void {
    this.editForm.patchValue({
      id: sexe.id,
      code: sexe.code,
      libAr: sexe.libAr,
      libEn: sexe.libEn,
      util: sexe.util,
      dateop: sexe.dateop ? sexe.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: sexe.modifiedBy,
      op: sexe.op,
      isDeleted: sexe.isDeleted,
      createdDate: sexe.createdDate ? sexe.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: sexe.modifiedDate ? sexe.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISexe {
    return {
      ...new Sexe(),
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
