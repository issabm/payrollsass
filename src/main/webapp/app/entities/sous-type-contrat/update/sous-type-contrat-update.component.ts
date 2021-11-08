import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISousTypeContrat, SousTypeContrat } from '../sous-type-contrat.model';
import { SousTypeContratService } from '../service/sous-type-contrat.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

@Component({
  selector: 'jhi-sous-type-contrat-update',
  templateUrl: './sous-type-contrat-update.component.html',
})
export class SousTypeContratUpdateComponent implements OnInit {
  isSaving = false;

  typeContratsSharedCollection: ITypeContrat[] = [];

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
    typeContrat: [],
  });

  constructor(
    protected sousTypeContratService: SousTypeContratService,
    protected typeContratService: TypeContratService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sousTypeContrat }) => {
      if (sousTypeContrat.id === undefined) {
        const today = dayjs().startOf('day');
        sousTypeContrat.dateop = today;
        sousTypeContrat.createdDate = today;
        sousTypeContrat.modifiedDate = today;
      }

      this.updateForm(sousTypeContrat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sousTypeContrat = this.createFromForm();
    if (sousTypeContrat.id !== undefined) {
      this.subscribeToSaveResponse(this.sousTypeContratService.update(sousTypeContrat));
    } else {
      this.subscribeToSaveResponse(this.sousTypeContratService.create(sousTypeContrat));
    }
  }

  trackTypeContratById(index: number, item: ITypeContrat): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISousTypeContrat>>): void {
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

  protected updateForm(sousTypeContrat: ISousTypeContrat): void {
    this.editForm.patchValue({
      id: sousTypeContrat.id,
      code: sousTypeContrat.code,
      libAr: sousTypeContrat.libAr,
      libEn: sousTypeContrat.libEn,
      util: sousTypeContrat.util,
      dateop: sousTypeContrat.dateop ? sousTypeContrat.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: sousTypeContrat.modifiedBy,
      op: sousTypeContrat.op,
      isDeleted: sousTypeContrat.isDeleted,
      createdDate: sousTypeContrat.createdDate ? sousTypeContrat.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: sousTypeContrat.modifiedDate ? sousTypeContrat.modifiedDate.format(DATE_TIME_FORMAT) : null,
      typeContrat: sousTypeContrat.typeContrat,
    });

    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing(
      this.typeContratsSharedCollection,
      sousTypeContrat.typeContrat
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing(typeContrats, this.editForm.get('typeContrat')!.value)
        )
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));
  }

  protected createFromForm(): ISousTypeContrat {
    return {
      ...new SousTypeContrat(),
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
      typeContrat: this.editForm.get(['typeContrat'])!.value,
    };
  }
}
