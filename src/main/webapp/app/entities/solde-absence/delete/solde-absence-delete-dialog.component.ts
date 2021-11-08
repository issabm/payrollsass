import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISoldeAbsence } from '../solde-absence.model';
import { SoldeAbsenceService } from '../service/solde-absence.service';

@Component({
  templateUrl: './solde-absence-delete-dialog.component.html',
})
export class SoldeAbsenceDeleteDialogComponent {
  soldeAbsence?: ISoldeAbsence;

  constructor(protected soldeAbsenceService: SoldeAbsenceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.soldeAbsenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
