import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IManagementResourceFun } from '../management-resource-fun.model';
import { ManagementResourceFunService } from '../service/management-resource-fun.service';

@Component({
  templateUrl: './management-resource-fun-delete-dialog.component.html',
})
export class ManagementResourceFunDeleteDialogComponent {
  managementResourceFun?: IManagementResourceFun;

  constructor(protected managementResourceFunService: ManagementResourceFunService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.managementResourceFunService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
