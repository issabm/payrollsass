import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldeAbsencePaie, SoldeAbsencePaie } from '../solde-absence-paie.model';
import { SoldeAbsencePaieService } from '../service/solde-absence-paie.service';
import { IPayroll } from 'app/entities/payroll/payroll.model';
import { PayrollService } from 'app/entities/payroll/service/payroll.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';

@Component({
  selector: 'jhi-solde-absence-paie-update',
  templateUrl: './solde-absence-paie-update.component.html',
})
export class SoldeAbsencePaieUpdateComponent implements OnInit {
  isSaving = false;

  payrollsSharedCollection: IPayroll[] = [];
  natureAbsencesSharedCollection: INatureAbsence[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [],
    mois: [],
    nbDays: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    payroll: [],
    natureAbsence: [],
  });

  constructor(
    protected soldeAbsencePaieService: SoldeAbsencePaieService,
    protected payrollService: PayrollService,
    protected natureAbsenceService: NatureAbsenceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsencePaie }) => {
      if (soldeAbsencePaie.id === undefined) {
        const today = dayjs().startOf('day');
        soldeAbsencePaie.dateop = today;
        soldeAbsencePaie.createdDate = today;
        soldeAbsencePaie.modifiedDate = today;
      }

      this.updateForm(soldeAbsencePaie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldeAbsencePaie = this.createFromForm();
    if (soldeAbsencePaie.id !== undefined) {
      this.subscribeToSaveResponse(this.soldeAbsencePaieService.update(soldeAbsencePaie));
    } else {
      this.subscribeToSaveResponse(this.soldeAbsencePaieService.create(soldeAbsencePaie));
    }
  }

  trackPayrollById(index: number, item: IPayroll): number {
    return item.id!;
  }

  trackNatureAbsenceById(index: number, item: INatureAbsence): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldeAbsencePaie>>): void {
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

  protected updateForm(soldeAbsencePaie: ISoldeAbsencePaie): void {
    this.editForm.patchValue({
      id: soldeAbsencePaie.id,
      annee: soldeAbsencePaie.annee,
      mois: soldeAbsencePaie.mois,
      nbDays: soldeAbsencePaie.nbDays,
      util: soldeAbsencePaie.util,
      dateop: soldeAbsencePaie.dateop ? soldeAbsencePaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: soldeAbsencePaie.modifiedBy,
      op: soldeAbsencePaie.op,
      isDeleted: soldeAbsencePaie.isDeleted,
      createdDate: soldeAbsencePaie.createdDate ? soldeAbsencePaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: soldeAbsencePaie.modifiedDate ? soldeAbsencePaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
      payroll: soldeAbsencePaie.payroll,
      natureAbsence: soldeAbsencePaie.natureAbsence,
    });

    this.payrollsSharedCollection = this.payrollService.addPayrollToCollectionIfMissing(
      this.payrollsSharedCollection,
      soldeAbsencePaie.payroll
    );
    this.natureAbsencesSharedCollection = this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(
      this.natureAbsencesSharedCollection,
      soldeAbsencePaie.natureAbsence
    );
  }

  protected loadRelationshipsOptions(): void {
    this.payrollService
      .query()
      .pipe(map((res: HttpResponse<IPayroll[]>) => res.body ?? []))
      .pipe(
        map((payrolls: IPayroll[]) => this.payrollService.addPayrollToCollectionIfMissing(payrolls, this.editForm.get('payroll')!.value))
      )
      .subscribe((payrolls: IPayroll[]) => (this.payrollsSharedCollection = payrolls));

    this.natureAbsenceService
      .query()
      .pipe(map((res: HttpResponse<INatureAbsence[]>) => res.body ?? []))
      .pipe(
        map((natureAbsences: INatureAbsence[]) =>
          this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(natureAbsences, this.editForm.get('natureAbsence')!.value)
        )
      )
      .subscribe((natureAbsences: INatureAbsence[]) => (this.natureAbsencesSharedCollection = natureAbsences));
  }

  protected createFromForm(): ISoldeAbsencePaie {
    return {
      ...new SoldeAbsencePaie(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      nbDays: this.editForm.get(['nbDays'])!.value,
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
      payroll: this.editForm.get(['payroll'])!.value,
      natureAbsence: this.editForm.get(['natureAbsence'])!.value,
    };
  }
}
