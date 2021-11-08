import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISituationFamiliale, SituationFamiliale } from '../situation-familiale.model';
import { SituationFamilialeService } from '../service/situation-familiale.service';

@Component({
  selector: 'jhi-situation-familiale-update',
  templateUrl: './situation-familiale-update.component.html',
})
export class SituationFamilialeUpdateComponent implements OnInit {
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
    protected situationFamilialeService: SituationFamilialeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situationFamiliale }) => {
      if (situationFamiliale.id === undefined) {
        const today = dayjs().startOf('day');
        situationFamiliale.dateop = today;
        situationFamiliale.createdDate = today;
        situationFamiliale.modifiedDate = today;
      }

      this.updateForm(situationFamiliale);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situationFamiliale = this.createFromForm();
    if (situationFamiliale.id !== undefined) {
      this.subscribeToSaveResponse(this.situationFamilialeService.update(situationFamiliale));
    } else {
      this.subscribeToSaveResponse(this.situationFamilialeService.create(situationFamiliale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituationFamiliale>>): void {
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

  protected updateForm(situationFamiliale: ISituationFamiliale): void {
    this.editForm.patchValue({
      id: situationFamiliale.id,
      code: situationFamiliale.code,
      libAr: situationFamiliale.libAr,
      libEn: situationFamiliale.libEn,
      util: situationFamiliale.util,
      dateop: situationFamiliale.dateop ? situationFamiliale.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: situationFamiliale.modifiedBy,
      op: situationFamiliale.op,
      isDeleted: situationFamiliale.isDeleted,
      createdDate: situationFamiliale.createdDate ? situationFamiliale.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: situationFamiliale.modifiedDate ? situationFamiliale.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISituationFamiliale {
    return {
      ...new SituationFamiliale(),
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
