import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPlateInfo, PlateInfo } from '../plate-info.model';
import { PlateInfoService } from '../service/plate-info.service';
import { IPlate } from 'app/entities/plate/plate.model';
import { PlateService } from 'app/entities/plate/service/plate.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';

@Component({
  selector: 'jhi-plate-info-update',
  templateUrl: './plate-info-update.component.html',
})
export class PlateInfoUpdateComponent implements OnInit {
  isSaving = false;

  platesSharedCollection: IPlate[] = [];
  rebriquesSharedCollection: IRebrique[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    lib: [],
    valTaken: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    plate: [],
    rebrique: [],
  });

  constructor(
    protected plateInfoService: PlateInfoService,
    protected plateService: PlateService,
    protected rebriqueService: RebriqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plateInfo }) => {
      if (plateInfo.id === undefined) {
        const today = dayjs().startOf('day');
        plateInfo.dateop = today;
        plateInfo.createdDate = today;
        plateInfo.modifiedDate = today;
      }

      this.updateForm(plateInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plateInfo = this.createFromForm();
    if (plateInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.plateInfoService.update(plateInfo));
    } else {
      this.subscribeToSaveResponse(this.plateInfoService.create(plateInfo));
    }
  }

  trackPlateById(index: number, item: IPlate): number {
    return item.id!;
  }

  trackRebriqueById(index: number, item: IRebrique): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlateInfo>>): void {
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

  protected updateForm(plateInfo: IPlateInfo): void {
    this.editForm.patchValue({
      id: plateInfo.id,
      code: plateInfo.code,
      lib: plateInfo.lib,
      valTaken: plateInfo.valTaken,
      dateSituation: plateInfo.dateSituation,
      dateop: plateInfo.dateop ? plateInfo.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: plateInfo.modifiedBy,
      createdBy: plateInfo.createdBy,
      op: plateInfo.op,
      util: plateInfo.util,
      isDeleted: plateInfo.isDeleted,
      createdDate: plateInfo.createdDate ? plateInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: plateInfo.modifiedDate ? plateInfo.modifiedDate.format(DATE_TIME_FORMAT) : null,
      plate: plateInfo.plate,
      rebrique: plateInfo.rebrique,
    });

    this.platesSharedCollection = this.plateService.addPlateToCollectionIfMissing(this.platesSharedCollection, plateInfo.plate);
    this.rebriquesSharedCollection = this.rebriqueService.addRebriqueToCollectionIfMissing(
      this.rebriquesSharedCollection,
      plateInfo.rebrique
    );
  }

  protected loadRelationshipsOptions(): void {
    this.plateService
      .query()
      .pipe(map((res: HttpResponse<IPlate[]>) => res.body ?? []))
      .pipe(map((plates: IPlate[]) => this.plateService.addPlateToCollectionIfMissing(plates, this.editForm.get('plate')!.value)))
      .subscribe((plates: IPlate[]) => (this.platesSharedCollection = plates));

    this.rebriqueService
      .query()
      .pipe(map((res: HttpResponse<IRebrique[]>) => res.body ?? []))
      .pipe(
        map((rebriques: IRebrique[]) =>
          this.rebriqueService.addRebriqueToCollectionIfMissing(rebriques, this.editForm.get('rebrique')!.value)
        )
      )
      .subscribe((rebriques: IRebrique[]) => (this.rebriquesSharedCollection = rebriques));
  }

  protected createFromForm(): IPlateInfo {
    return {
      ...new PlateInfo(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      lib: this.editForm.get(['lib'])!.value,
      valTaken: this.editForm.get(['valTaken'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
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
      plate: this.editForm.get(['plate'])!.value,
      rebrique: this.editForm.get(['rebrique'])!.value,
    };
  }
}
