import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayrollInfo } from '../payroll-info.model';
import { PayrollInfoService } from '../service/payroll-info.service';

@Component({
  templateUrl: './payroll-info-delete-dialog.component.html',
})
export class PayrollInfoDeleteDialogComponent {
  payrollInfo?: IPayrollInfo;

  constructor(protected payrollInfoService: PayrollInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payrollInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
