import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAdhesion, Adhesion } from '../adhesion.model';
import { AdhesionService } from '../service/adhesion.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { IEntityAdhesion } from 'app/entities/entity-adhesion/entity-adhesion.model';
import { EntityAdhesionService } from 'app/entities/entity-adhesion/service/entity-adhesion.service';

@Component({
  selector: 'jhi-adhesion-update',
  templateUrl: './adhesion-update.component.html',
})
export class AdhesionUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];
  entityAdhesionsSharedCollection: IEntityAdhesion[] = [];

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    employe: [],
    entityAdhesion: [],
  });

  constructor(
    protected adhesionService: AdhesionService,
    protected employeService: EmployeService,
    protected entityAdhesionService: EntityAdhesionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adhesion }) => {
      if (adhesion.id === undefined) {
        const today = dayjs().startOf('day');
        adhesion.dateop = today;
        adhesion.createdDate = today;
        adhesion.modifiedDate = today;
      }

      this.updateForm(adhesion);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adhesion = this.createFromForm();
    if (adhesion.id !== undefined) {
      this.subscribeToSaveResponse(this.adhesionService.update(adhesion));
    } else {
      this.subscribeToSaveResponse(this.adhesionService.create(adhesion));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackEntityAdhesionById(index: number, item: IEntityAdhesion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdhesion>>): void {
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

  protected updateForm(adhesion: IAdhesion): void {
    this.editForm.patchValue({
      id: adhesion.id,
      dateDebut: adhesion.dateDebut,
      dateFin: adhesion.dateFin,
      util: adhesion.util,
      dateop: adhesion.dateop ? adhesion.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: adhesion.modifiedBy,
      op: adhesion.op,
      isDeleted: adhesion.isDeleted,
      createdDate: adhesion.createdDate ? adhesion.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: adhesion.modifiedDate ? adhesion.modifiedDate.format(DATE_TIME_FORMAT) : null,
      employe: adhesion.employe,
      entityAdhesion: adhesion.entityAdhesion,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(this.employesSharedCollection, adhesion.employe);
    this.entityAdhesionsSharedCollection = this.entityAdhesionService.addEntityAdhesionToCollectionIfMissing(
      this.entityAdhesionsSharedCollection,
      adhesion.entityAdhesion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) => this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('employe')!.value))
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));

    this.entityAdhesionService
      .query()
      .pipe(map((res: HttpResponse<IEntityAdhesion[]>) => res.body ?? []))
      .pipe(
        map((entityAdhesions: IEntityAdhesion[]) =>
          this.entityAdhesionService.addEntityAdhesionToCollectionIfMissing(entityAdhesions, this.editForm.get('entityAdhesion')!.value)
        )
      )
      .subscribe((entityAdhesions: IEntityAdhesion[]) => (this.entityAdhesionsSharedCollection = entityAdhesions));
  }

  protected createFromForm(): IAdhesion {
    return {
      ...new Adhesion(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
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
      employe: this.editForm.get(['employe'])!.value,
      entityAdhesion: this.editForm.get(['entityAdhesion'])!.value,
    };
  }
}
