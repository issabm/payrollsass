import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureAdhesion, NatureAdhesion } from '../nature-adhesion.model';
import { NatureAdhesionService } from '../service/nature-adhesion.service';

@Component({
  selector: 'jhi-nature-adhesion-update',
  templateUrl: './nature-adhesion-update.component.html',
})
export class NatureAdhesionUpdateComponent implements OnInit {
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

  constructor(
    protected natureAdhesionService: NatureAdhesionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAdhesion }) => {
      if (natureAdhesion.id === undefined) {
        const today = dayjs().startOf('day');
        natureAdhesion.dateop = today;
        natureAdhesion.createdDate = today;
        natureAdhesion.modifiedDate = today;
      }

      this.updateForm(natureAdhesion);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureAdhesion = this.createFromForm();
    if (natureAdhesion.id !== undefined) {
      this.subscribeToSaveResponse(this.natureAdhesionService.update(natureAdhesion));
    } else {
      this.subscribeToSaveResponse(this.natureAdhesionService.create(natureAdhesion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureAdhesion>>): void {
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

  protected updateForm(natureAdhesion: INatureAdhesion): void {
    this.editForm.patchValue({
      id: natureAdhesion.id,
      code: natureAdhesion.code,
      libAr: natureAdhesion.libAr,
      libEn: natureAdhesion.libEn,
      util: natureAdhesion.util,
      dateop: natureAdhesion.dateop ? natureAdhesion.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: natureAdhesion.modifiedBy,
      op: natureAdhesion.op,
      isDeleted: natureAdhesion.isDeleted,
      createdDate: natureAdhesion.createdDate ? natureAdhesion.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureAdhesion.modifiedDate ? natureAdhesion.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INatureAdhesion {
    return {
      ...new NatureAdhesion(),
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
