import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFamille, Famille } from '../famille.model';
import { FamilleService } from '../service/famille.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISituationFamiliale } from 'app/entities/situation-familiale/situation-familiale.model';
import { SituationFamilialeService } from 'app/entities/situation-familiale/service/situation-familiale.service';
import { IConjoint } from 'app/entities/conjoint/conjoint.model';
import { ConjointService } from 'app/entities/conjoint/service/conjoint.service';

@Component({
  selector: 'jhi-famille-update',
  templateUrl: './famille-update.component.html',
})
export class FamilleUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];
  situationFamilialesSharedCollection: ISituationFamiliale[] = [];
  conjointsSharedCollection: IConjoint[] = [];

  editForm = this.fb.group({
    id: [],
    dateSituation: [],
    dateop: [],
    util: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    employe: [],
    situationFamiliale: [],
    conjoint: [],
  });

  constructor(
    protected familleService: FamilleService,
    protected employeService: EmployeService,
    protected situationFamilialeService: SituationFamilialeService,
    protected conjointService: ConjointService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ famille }) => {
      if (famille.id === undefined) {
        const today = dayjs().startOf('day');
        famille.dateop = today;
        famille.createdDate = today;
        famille.modifiedDate = today;
      }

      this.updateForm(famille);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const famille = this.createFromForm();
    if (famille.id !== undefined) {
      this.subscribeToSaveResponse(this.familleService.update(famille));
    } else {
      this.subscribeToSaveResponse(this.familleService.create(famille));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackSituationFamilialeById(index: number, item: ISituationFamiliale): number {
    return item.id!;
  }

  trackConjointById(index: number, item: IConjoint): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamille>>): void {
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

  protected updateForm(famille: IFamille): void {
    this.editForm.patchValue({
      id: famille.id,
      dateSituation: famille.dateSituation,
      dateop: famille.dateop ? famille.dateop.format(DATE_TIME_FORMAT) : null,
      util: famille.util,
      modifiedBy: famille.modifiedBy,
      op: famille.op,
      isDeleted: famille.isDeleted,
      createdDate: famille.createdDate ? famille.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: famille.modifiedDate ? famille.modifiedDate.format(DATE_TIME_FORMAT) : null,
      employe: famille.employe,
      situationFamiliale: famille.situationFamiliale,
      conjoint: famille.conjoint,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(this.employesSharedCollection, famille.employe);
    this.situationFamilialesSharedCollection = this.situationFamilialeService.addSituationFamilialeToCollectionIfMissing(
      this.situationFamilialesSharedCollection,
      famille.situationFamiliale
    );
    this.conjointsSharedCollection = this.conjointService.addConjointToCollectionIfMissing(
      this.conjointsSharedCollection,
      famille.conjoint
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

    this.situationFamilialeService
      .query()
      .pipe(map((res: HttpResponse<ISituationFamiliale[]>) => res.body ?? []))
      .pipe(
        map((situationFamiliales: ISituationFamiliale[]) =>
          this.situationFamilialeService.addSituationFamilialeToCollectionIfMissing(
            situationFamiliales,
            this.editForm.get('situationFamiliale')!.value
          )
        )
      )
      .subscribe((situationFamiliales: ISituationFamiliale[]) => (this.situationFamilialesSharedCollection = situationFamiliales));

    this.conjointService
      .query()
      .pipe(map((res: HttpResponse<IConjoint[]>) => res.body ?? []))
      .pipe(
        map((conjoints: IConjoint[]) =>
          this.conjointService.addConjointToCollectionIfMissing(conjoints, this.editForm.get('conjoint')!.value)
        )
      )
      .subscribe((conjoints: IConjoint[]) => (this.conjointsSharedCollection = conjoints));
  }

  protected createFromForm(): IFamille {
    return {
      ...new Famille(),
      id: this.editForm.get(['id'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      util: this.editForm.get(['util'])!.value,
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
      situationFamiliale: this.editForm.get(['situationFamiliale'])!.value,
      conjoint: this.editForm.get(['conjoint'])!.value,
    };
  }
}
