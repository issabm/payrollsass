import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConcerne, Concerne } from '../concerne.model';
import { ConcerneService } from '../service/concerne.service';

@Component({
  selector: 'jhi-concerne-update',
  templateUrl: './concerne-update.component.html',
})
export class ConcerneUpdateComponent implements OnInit {
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

  constructor(protected concerneService: ConcerneService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concerne }) => {
      if (concerne.id === undefined) {
        const today = dayjs().startOf('day');
        concerne.dateop = today;
        concerne.createdDate = today;
        concerne.modifiedDate = today;
      }

      this.updateForm(concerne);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concerne = this.createFromForm();
    if (concerne.id !== undefined) {
      this.subscribeToSaveResponse(this.concerneService.update(concerne));
    } else {
      this.subscribeToSaveResponse(this.concerneService.create(concerne));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcerne>>): void {
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

  protected updateForm(concerne: IConcerne): void {
    this.editForm.patchValue({
      id: concerne.id,
      code: concerne.code,
      libAr: concerne.libAr,
      libEn: concerne.libEn,
      util: concerne.util,
      dateop: concerne.dateop ? concerne.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: concerne.modifiedBy,
      op: concerne.op,
      isDeleted: concerne.isDeleted,
      createdDate: concerne.createdDate ? concerne.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: concerne.modifiedDate ? concerne.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IConcerne {
    return {
      ...new Concerne(),
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
