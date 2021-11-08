import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPayrollInfo, PayrollInfo } from '../payroll-info.model';
import { PayrollInfoService } from '../service/payroll-info.service';
import { IPayroll } from 'app/entities/payroll/payroll.model';
import { PayrollService } from 'app/entities/payroll/service/payroll.service';

@Component({
  selector: 'jhi-payroll-info-update',
  templateUrl: './payroll-info-update.component.html',
})
export class PayrollInfoUpdateComponent implements OnInit {
  isSaving = false;

  payrollsSharedCollection: IPayroll[] = [];

  editForm = this.fb.group({
    id: [],
    util: [],
    code: [],
    libAr: [],
    libFr: [],
    libEr: [],
    valueRebrique: [],
    valueRebDevise: [],
    tauxChange: [],
    dateCalcul: [],
    dateEffect: [],
    calculBy: [],
    effectBy: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    payroll: [],
  });

  constructor(
    protected payrollInfoService: PayrollInfoService,
    protected payrollService: PayrollService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payrollInfo }) => {
      if (payrollInfo.id === undefined) {
        const today = dayjs().startOf('day');
        payrollInfo.dateop = today;
        payrollInfo.createdDate = today;
        payrollInfo.modifiedDate = today;
      }

      this.updateForm(payrollInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payrollInfo = this.createFromForm();
    if (payrollInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.payrollInfoService.update(payrollInfo));
    } else {
      this.subscribeToSaveResponse(this.payrollInfoService.create(payrollInfo));
    }
  }

  trackPayrollById(index: number, item: IPayroll): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayrollInfo>>): void {
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

  protected updateForm(payrollInfo: IPayrollInfo): void {
    this.editForm.patchValue({
      id: payrollInfo.id,
      util: payrollInfo.util,
      code: payrollInfo.code,
      libAr: payrollInfo.libAr,
      libFr: payrollInfo.libFr,
      libEr: payrollInfo.libEr,
      valueRebrique: payrollInfo.valueRebrique,
      valueRebDevise: payrollInfo.valueRebDevise,
      tauxChange: payrollInfo.tauxChange,
      dateCalcul: payrollInfo.dateCalcul,
      dateEffect: payrollInfo.dateEffect,
      calculBy: payrollInfo.calculBy,
      effectBy: payrollInfo.effectBy,
      dateSituation: payrollInfo.dateSituation,
      dateop: payrollInfo.dateop ? payrollInfo.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: payrollInfo.modifiedBy,
      createdBy: payrollInfo.createdBy,
      op: payrollInfo.op,
      isDeleted: payrollInfo.isDeleted,
      createdDate: payrollInfo.createdDate ? payrollInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: payrollInfo.modifiedDate ? payrollInfo.modifiedDate.format(DATE_TIME_FORMAT) : null,
      payroll: payrollInfo.payroll,
    });

    this.payrollsSharedCollection = this.payrollService.addPayrollToCollectionIfMissing(this.payrollsSharedCollection, payrollInfo.payroll);
  }

  protected loadRelationshipsOptions(): void {
    this.payrollService
      .query()
      .pipe(map((res: HttpResponse<IPayroll[]>) => res.body ?? []))
      .pipe(
        map((payrolls: IPayroll[]) => this.payrollService.addPayrollToCollectionIfMissing(payrolls, this.editForm.get('payroll')!.value))
      )
      .subscribe((payrolls: IPayroll[]) => (this.payrollsSharedCollection = payrolls));
  }

  protected createFromForm(): IPayrollInfo {
    return {
      ...new PayrollInfo(),
      id: this.editForm.get(['id'])!.value,
      util: this.editForm.get(['util'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libFr: this.editForm.get(['libFr'])!.value,
      libEr: this.editForm.get(['libEr'])!.value,
      valueRebrique: this.editForm.get(['valueRebrique'])!.value,
      valueRebDevise: this.editForm.get(['valueRebDevise'])!.value,
      tauxChange: this.editForm.get(['tauxChange'])!.value,
      dateCalcul: this.editForm.get(['dateCalcul'])!.value,
      dateEffect: this.editForm.get(['dateEffect'])!.value,
      calculBy: this.editForm.get(['calculBy'])!.value,
      effectBy: this.editForm.get(['effectBy'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      payroll: this.editForm.get(['payroll'])!.value,
    };
  }
}
