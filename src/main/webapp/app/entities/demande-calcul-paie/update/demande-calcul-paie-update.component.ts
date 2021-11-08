import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDemandeCalculPaie, DemandeCalculPaie } from '../demande-calcul-paie.model';
import { DemandeCalculPaieService } from '../service/demande-calcul-paie.service';

@Component({
  selector: 'jhi-demande-calcul-paie-update',
  templateUrl: './demande-calcul-paie-update.component.html',
})
export class DemandeCalculPaieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    lib: [],
    annee: [],
    mois: [],
    dateCalcul: [],
    dateValid: [],
    datePayroll: [],
    totalNet: [],
    totalNetDevise: [],
    tauxChange: [],
    calculBy: [],
    effectBy: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(
    protected demandeCalculPaieService: DemandeCalculPaieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeCalculPaie }) => {
      if (demandeCalculPaie.id === undefined) {
        const today = dayjs().startOf('day');
        demandeCalculPaie.dateop = today;
        demandeCalculPaie.createdDate = today;
        demandeCalculPaie.modifiedDate = today;
      }

      this.updateForm(demandeCalculPaie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeCalculPaie = this.createFromForm();
    if (demandeCalculPaie.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeCalculPaieService.update(demandeCalculPaie));
    } else {
      this.subscribeToSaveResponse(this.demandeCalculPaieService.create(demandeCalculPaie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeCalculPaie>>): void {
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

  protected updateForm(demandeCalculPaie: IDemandeCalculPaie): void {
    this.editForm.patchValue({
      id: demandeCalculPaie.id,
      code: demandeCalculPaie.code,
      lib: demandeCalculPaie.lib,
      annee: demandeCalculPaie.annee,
      mois: demandeCalculPaie.mois,
      dateCalcul: demandeCalculPaie.dateCalcul,
      dateValid: demandeCalculPaie.dateValid,
      datePayroll: demandeCalculPaie.datePayroll,
      totalNet: demandeCalculPaie.totalNet,
      totalNetDevise: demandeCalculPaie.totalNetDevise,
      tauxChange: demandeCalculPaie.tauxChange,
      calculBy: demandeCalculPaie.calculBy,
      effectBy: demandeCalculPaie.effectBy,
      dateSituation: demandeCalculPaie.dateSituation,
      dateop: demandeCalculPaie.dateop ? demandeCalculPaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: demandeCalculPaie.modifiedBy,
      createdBy: demandeCalculPaie.createdBy,
      op: demandeCalculPaie.op,
      util: demandeCalculPaie.util,
      isDeleted: demandeCalculPaie.isDeleted,
      createdDate: demandeCalculPaie.createdDate ? demandeCalculPaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: demandeCalculPaie.modifiedDate ? demandeCalculPaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDemandeCalculPaie {
    return {
      ...new DemandeCalculPaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      lib: this.editForm.get(['lib'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      dateCalcul: this.editForm.get(['dateCalcul'])!.value,
      dateValid: this.editForm.get(['dateValid'])!.value,
      datePayroll: this.editForm.get(['datePayroll'])!.value,
      totalNet: this.editForm.get(['totalNet'])!.value,
      totalNetDevise: this.editForm.get(['totalNetDevise'])!.value,
      tauxChange: this.editForm.get(['tauxChange'])!.value,
      calculBy: this.editForm.get(['calculBy'])!.value,
      effectBy: this.editForm.get(['effectBy'])!.value,
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
    };
  }
}
