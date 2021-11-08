import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISoldeAbsencePaie } from '../solde-absence-paie.model';
import { SoldeAbsencePaieService } from '../service/solde-absence-paie.service';

@Component({
  templateUrl: './solde-absence-paie-delete-dialog.component.html',
})
export class SoldeAbsencePaieDeleteDialogComponent {
  soldeAbsencePaie?: ISoldeAbsencePaie;

  constructor(protected soldeAbsencePaieService: SoldeAbsencePaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.soldeAbsencePaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
