import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPayroll, Payroll } from '../payroll.model';
import { PayrollService } from '../service/payroll.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';
import { IMouvementPaie } from 'app/entities/mouvement-paie/mouvement-paie.model';
import { MouvementPaieService } from 'app/entities/mouvement-paie/service/mouvement-paie.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

@Component({
  selector: 'jhi-payroll-update',
  templateUrl: './payroll-update.component.html',
})
export class PayrollUpdateComponent implements OnInit {
  isSaving = false;

  devisesSharedCollection: IDevise[] = [];
  mouvementPaiesSharedCollection: IMouvementPaie[] = [];
  situationsSharedCollection: ISituation[] = [];

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
    devise: [],
    mvt: [],
    situation: [],
  });

  constructor(
    protected payrollService: PayrollService,
    protected deviseService: DeviseService,
    protected mouvementPaieService: MouvementPaieService,
    protected situationService: SituationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payroll }) => {
      if (payroll.id === undefined) {
        const today = dayjs().startOf('day');
        payroll.dateop = today;
        payroll.createdDate = today;
        payroll.modifiedDate = today;
      }

      this.updateForm(payroll);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payroll = this.createFromForm();
    if (payroll.id !== undefined) {
      this.subscribeToSaveResponse(this.payrollService.update(payroll));
    } else {
      this.subscribeToSaveResponse(this.payrollService.create(payroll));
    }
  }

  trackDeviseById(index: number, item: IDevise): number {
    return item.id!;
  }

  trackMouvementPaieById(index: number, item: IMouvementPaie): number {
    return item.id!;
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayroll>>): void {
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

  protected updateForm(payroll: IPayroll): void {
    this.editForm.patchValue({
      id: payroll.id,
      code: payroll.code,
      lib: payroll.lib,
      annee: payroll.annee,
      mois: payroll.mois,
      dateCalcul: payroll.dateCalcul,
      dateValid: payroll.dateValid,
      datePayroll: payroll.datePayroll,
      totalNet: payroll.totalNet,
      totalNetDevise: payroll.totalNetDevise,
      tauxChange: payroll.tauxChange,
      calculBy: payroll.calculBy,
      effectBy: payroll.effectBy,
      dateSituation: payroll.dateSituation,
      dateop: payroll.dateop ? payroll.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: payroll.modifiedBy,
      createdBy: payroll.createdBy,
      op: payroll.op,
      util: payroll.util,
      isDeleted: payroll.isDeleted,
      createdDate: payroll.createdDate ? payroll.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: payroll.modifiedDate ? payroll.modifiedDate.format(DATE_TIME_FORMAT) : null,
      devise: payroll.devise,
      mvt: payroll.mvt,
      situation: payroll.situation,
    });

    this.devisesSharedCollection = this.deviseService.addDeviseToCollectionIfMissing(this.devisesSharedCollection, payroll.devise);
    this.mouvementPaiesSharedCollection = this.mouvementPaieService.addMouvementPaieToCollectionIfMissing(
      this.mouvementPaiesSharedCollection,
      payroll.mvt
    );
    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      payroll.situation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.deviseService
      .query()
      .pipe(map((res: HttpResponse<IDevise[]>) => res.body ?? []))
      .pipe(map((devises: IDevise[]) => this.deviseService.addDeviseToCollectionIfMissing(devises, this.editForm.get('devise')!.value)))
      .subscribe((devises: IDevise[]) => (this.devisesSharedCollection = devises));

    this.mouvementPaieService
      .query()
      .pipe(map((res: HttpResponse<IMouvementPaie[]>) => res.body ?? []))
      .pipe(
        map((mouvementPaies: IMouvementPaie[]) =>
          this.mouvementPaieService.addMouvementPaieToCollectionIfMissing(mouvementPaies, this.editForm.get('mvt')!.value)
        )
      )
      .subscribe((mouvementPaies: IMouvementPaie[]) => (this.mouvementPaiesSharedCollection = mouvementPaies));

    this.situationService
      .query()
      .pipe(map((res: HttpResponse<ISituation[]>) => res.body ?? []))
      .pipe(
        map((situations: ISituation[]) =>
          this.situationService.addSituationToCollectionIfMissing(situations, this.editForm.get('situation')!.value)
        )
      )
      .subscribe((situations: ISituation[]) => (this.situationsSharedCollection = situations));
  }

  protected createFromForm(): IPayroll {
    return {
      ...new Payroll(),
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
      devise: this.editForm.get(['devise'])!.value,
      mvt: this.editForm.get(['mvt'])!.value,
      situation: this.editForm.get(['situation'])!.value,
    };
  }
}
