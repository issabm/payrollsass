import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAffiliation } from '../affiliation.model';
import { AffiliationService } from '../service/affiliation.service';

@Component({
  templateUrl: './affiliation-delete-dialog.component.html',
})
export class AffiliationDeleteDialogComponent {
  affiliation?: IAffiliation;

  constructor(protected affiliationService: AffiliationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affiliationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
