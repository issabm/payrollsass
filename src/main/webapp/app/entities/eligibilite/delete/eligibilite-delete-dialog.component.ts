import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEligibilite } from '../eligibilite.model';
import { EligibiliteService } from '../service/eligibilite.service';

@Component({
  templateUrl: './eligibilite-delete-dialog.component.html',
})
export class EligibiliteDeleteDialogComponent {
  eligibilite?: IEligibilite;

  constructor(protected eligibiliteService: EligibiliteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eligibiliteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
