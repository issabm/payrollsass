import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureConfig, NatureConfig } from '../nature-config.model';
import { NatureConfigService } from '../service/nature-config.service';

@Component({
  selector: 'jhi-nature-config-update',
  templateUrl: './nature-config-update.component.html',
})
export class NatureConfigUpdateComponent implements OnInit {
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

  constructor(protected natureConfigService: NatureConfigService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureConfig }) => {
      if (natureConfig.id === undefined) {
        const today = dayjs().startOf('day');
        natureConfig.dateop = today;
        natureConfig.createdDate = today;
        natureConfig.modifiedDate = today;
      }

      this.updateForm(natureConfig);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureConfig = this.createFromForm();
    if (natureConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.natureConfigService.update(natureConfig));
    } else {
      this.subscribeToSaveResponse(this.natureConfigService.create(natureConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureConfig>>): void {
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

  protected updateForm(natureConfig: INatureConfig): void {
    this.editForm.patchValue({
      id: natureConfig.id,
      code: natureConfig.code,
      libEn: natureConfig.libEn,
      libAr: natureConfig.libAr,
      libFr: natureConfig.libFr,
      dateop: natureConfig.dateop ? natureConfig.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: natureConfig.modifiedBy,
      createdBy: natureConfig.createdBy,
      op: natureConfig.op,
      util: natureConfig.util,
      isDeleted: natureConfig.isDeleted,
      createdDate: natureConfig.createdDate ? natureConfig.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureConfig.modifiedDate ? natureConfig.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INatureConfig {
    return {
      ...new NatureConfig(),
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
