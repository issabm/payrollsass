import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIdentite, Identite } from '../identite.model';
import { IdentiteService } from '../service/identite.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ITypeIdentite } from 'app/entities/type-identite/type-identite.model';
import { TypeIdentiteService } from 'app/entities/type-identite/service/type-identite.service';

@Component({
  selector: 'jhi-identite-update',
  templateUrl: './identite-update.component.html',
})
export class IdentiteUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];
  typeIdentitesSharedCollection: ITypeIdentite[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    dateIssued: [],
    placeIssed: [],
    dateVld: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    employe: [],
    typeIdentite: [],
  });

  constructor(
    protected identiteService: IdentiteService,
    protected employeService: EmployeService,
    protected typeIdentiteService: TypeIdentiteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identite }) => {
      if (identite.id === undefined) {
        const today = dayjs().startOf('day');
        identite.dateop = today;
        identite.createdDate = today;
        identite.modifiedDate = today;
      }

      this.updateForm(identite);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const identite = this.createFromForm();
    if (identite.id !== undefined) {
      this.subscribeToSaveResponse(this.identiteService.update(identite));
    } else {
      this.subscribeToSaveResponse(this.identiteService.create(identite));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackTypeIdentiteById(index: number, item: ITypeIdentite): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentite>>): void {
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

  protected updateForm(identite: IIdentite): void {
    this.editForm.patchValue({
      id: identite.id,
      code: identite.code,
      dateIssued: identite.dateIssued,
      placeIssed: identite.placeIssed,
      dateVld: identite.dateVld,
      util: identite.util,
      dateop: identite.dateop ? identite.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: identite.modifiedBy,
      op: identite.op,
      isDeleted: identite.isDeleted,
      createdDate: identite.createdDate ? identite.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: identite.modifiedDate ? identite.modifiedDate.format(DATE_TIME_FORMAT) : null,
      employe: identite.employe,
      typeIdentite: identite.typeIdentite,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(this.employesSharedCollection, identite.employe);
    this.typeIdentitesSharedCollection = this.typeIdentiteService.addTypeIdentiteToCollectionIfMissing(
      this.typeIdentitesSharedCollection,
      identite.typeIdentite
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

    this.typeIdentiteService
      .query()
      .pipe(map((res: HttpResponse<ITypeIdentite[]>) => res.body ?? []))
      .pipe(
        map((typeIdentites: ITypeIdentite[]) =>
          this.typeIdentiteService.addTypeIdentiteToCollectionIfMissing(typeIdentites, this.editForm.get('typeIdentite')!.value)
        )
      )
      .subscribe((typeIdentites: ITypeIdentite[]) => (this.typeIdentitesSharedCollection = typeIdentites));
  }

  protected createFromForm(): IIdentite {
    return {
      ...new Identite(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      dateIssued: this.editForm.get(['dateIssued'])!.value,
      placeIssed: this.editForm.get(['placeIssed'])!.value,
      dateVld: this.editForm.get(['dateVld'])!.value,
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
      typeIdentite: this.editForm.get(['typeIdentite'])!.value,
    };
  }
}
