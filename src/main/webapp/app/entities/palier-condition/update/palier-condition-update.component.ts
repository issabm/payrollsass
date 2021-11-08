import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPalierCondition, PalierCondition } from '../palier-condition.model';
import { PalierConditionService } from '../service/palier-condition.service';
import { IPalierPlate } from 'app/entities/palier-plate/palier-plate.model';
import { PalierPlateService } from 'app/entities/palier-plate/service/palier-plate.service';

@Component({
  selector: 'jhi-palier-condition-update',
  templateUrl: './palier-condition-update.component.html',
})
export class PalierConditionUpdateComponent implements OnInit {
  isSaving = false;

  palierPlatesSharedCollection: IPalierPlate[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    annee: [],
    minVal: [],
    maxVal: [],
    util: [],
    dateop: [],
    dateModif: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    palierPlate: [],
  });

  constructor(
    protected palierConditionService: PalierConditionService,
    protected palierPlateService: PalierPlateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierCondition }) => {
      if (palierCondition.id === undefined) {
        const today = dayjs().startOf('day');
        palierCondition.dateop = today;
        palierCondition.dateModif = today;
      }

      this.updateForm(palierCondition);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const palierCondition = this.createFromForm();
    if (palierCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.palierConditionService.update(palierCondition));
    } else {
      this.subscribeToSaveResponse(this.palierConditionService.create(palierCondition));
    }
  }

  trackPalierPlateById(index: number, item: IPalierPlate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPalierCondition>>): void {
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

  protected updateForm(palierCondition: IPalierCondition): void {
    this.editForm.patchValue({
      id: palierCondition.id,
      code: palierCondition.code,
      libEn: palierCondition.libEn,
      libAr: palierCondition.libAr,
      annee: palierCondition.annee,
      minVal: palierCondition.minVal,
      maxVal: palierCondition.maxVal,
      util: palierCondition.util,
      dateop: palierCondition.dateop ? palierCondition.dateop.format(DATE_TIME_FORMAT) : null,
      dateModif: palierCondition.dateModif ? palierCondition.dateModif.format(DATE_TIME_FORMAT) : null,
      modifiedBy: palierCondition.modifiedBy,
      op: palierCondition.op,
      isDeleted: palierCondition.isDeleted,
      palierPlate: palierCondition.palierPlate,
    });

    this.palierPlatesSharedCollection = this.palierPlateService.addPalierPlateToCollectionIfMissing(
      this.palierPlatesSharedCollection,
      palierCondition.palierPlate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.palierPlateService
      .query()
      .pipe(map((res: HttpResponse<IPalierPlate[]>) => res.body ?? []))
      .pipe(
        map((palierPlates: IPalierPlate[]) =>
          this.palierPlateService.addPalierPlateToCollectionIfMissing(palierPlates, this.editForm.get('palierPlate')!.value)
        )
      )
      .subscribe((palierPlates: IPalierPlate[]) => (this.palierPlatesSharedCollection = palierPlates));
  }

  protected createFromForm(): IPalierCondition {
    return {
      ...new PalierCondition(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      minVal: this.editForm.get(['minVal'])!.value,
      maxVal: this.editForm.get(['maxVal'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      dateModif: this.editForm.get(['dateModif'])!.value ? dayjs(this.editForm.get(['dateModif'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      palierPlate: this.editForm.get(['palierPlate'])!.value,
    };
  }
}
