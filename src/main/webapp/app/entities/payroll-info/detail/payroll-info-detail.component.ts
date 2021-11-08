import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayrollInfo } from '../payroll-info.model';

@Component({
  selector: 'jhi-payroll-info-detail',
  templateUrl: './payroll-info-detail.component.html',
})
export class PayrollInfoDetailComponent implements OnInit {
  payrollInfo: IPayrollInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payrollInfo }) => {
      this.payrollInfo = payrollInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
