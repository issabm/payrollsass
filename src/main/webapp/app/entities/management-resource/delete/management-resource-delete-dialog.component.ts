import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IManagementResource } from '../management-resource.model';
import { ManagementResourceService } from '../service/management-resource.service';

@Component({
  templateUrl: './management-resource-delete-dialog.component.html',
})
export class ManagementResourceDeleteDialogComponent {
  managementResource?: IManagementResource;

  constructor(protected managementResourceService: ManagementResourceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.managementResourceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
