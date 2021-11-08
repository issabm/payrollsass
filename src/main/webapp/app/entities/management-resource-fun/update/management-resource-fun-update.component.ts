import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IManagementResourceFun, ManagementResourceFun } from '../management-resource-fun.model';
import { ManagementResourceFunService } from '../service/management-resource-fun.service';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';
import { ManagementResourceService } from 'app/entities/management-resource/service/management-resource.service';

@Component({
  selector: 'jhi-management-resource-fun-update',
  templateUrl: './management-resource-fun-update.component.html',
})
export class ManagementResourceFunUpdateComponent implements OnInit {
  isSaving = false;

  managementResourcesSharedCollection: IManagementResource[] = [];

  editForm = this.fb.group({
    id: [],
    libEn: [],
    profile: [],
    enableAdd: [],
    enableCnst: [],
    enableDel: [],
    enableEd: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    ressourceManage: [],
  });

  constructor(
    protected managementResourceFunService: ManagementResourceFunService,
    protected managementResourceService: ManagementResourceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResourceFun }) => {
      if (managementResourceFun.id === undefined) {
        const today = dayjs().startOf('day');
        managementResourceFun.dateop = today;
        managementResourceFun.createdDate = today;
        managementResourceFun.modifiedDate = today;
      }

      this.updateForm(managementResourceFun);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const managementResourceFun = this.createFromForm();
    if (managementResourceFun.id !== undefined) {
      this.subscribeToSaveResponse(this.managementResourceFunService.update(managementResourceFun));
    } else {
      this.subscribeToSaveResponse(this.managementResourceFunService.create(managementResourceFun));
    }
  }

  trackManagementResourceById(index: number, item: IManagementResource): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManagementResourceFun>>): void {
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

  protected updateForm(managementResourceFun: IManagementResourceFun): void {
    this.editForm.patchValue({
      id: managementResourceFun.id,
      libEn: managementResourceFun.libEn,
      profile: managementResourceFun.profile,
      enableAdd: managementResourceFun.enableAdd,
      enableCnst: managementResourceFun.enableCnst,
      enableDel: managementResourceFun.enableDel,
      enableEd: managementResourceFun.enableEd,
      dateop: managementResourceFun.dateop ? managementResourceFun.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: managementResourceFun.modifiedBy,
      createdBy: managementResourceFun.createdBy,
      op: managementResourceFun.op,
      util: managementResourceFun.util,
      isDeleted: managementResourceFun.isDeleted,
      createdDate: managementResourceFun.createdDate ? managementResourceFun.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: managementResourceFun.modifiedDate ? managementResourceFun.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ressourceManage: managementResourceFun.ressourceManage,
    });

    this.managementResourcesSharedCollection = this.managementResourceService.addManagementResourceToCollectionIfMissing(
      this.managementResourcesSharedCollection,
      managementResourceFun.ressourceManage
    );
  }

  protected loadRelationshipsOptions(): void {
    this.managementResourceService
      .query()
      .pipe(map((res: HttpResponse<IManagementResource[]>) => res.body ?? []))
      .pipe(
        map((managementResources: IManagementResource[]) =>
          this.managementResourceService.addManagementResourceToCollectionIfMissing(
            managementResources,
            this.editForm.get('ressourceManage')!.value
          )
        )
      )
      .subscribe((managementResources: IManagementResource[]) => (this.managementResourcesSharedCollection = managementResources));
  }

  protected createFromForm(): IManagementResourceFun {
    return {
      ...new ManagementResourceFun(),
      id: this.editForm.get(['id'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      profile: this.editForm.get(['profile'])!.value,
      enableAdd: this.editForm.get(['enableAdd'])!.value,
      enableCnst: this.editForm.get(['enableCnst'])!.value,
      enableDel: this.editForm.get(['enableDel'])!.value,
      enableEd: this.editForm.get(['enableEd'])!.value,
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
      ressourceManage: this.editForm.get(['ressourceManage'])!.value,
    };
  }
}
