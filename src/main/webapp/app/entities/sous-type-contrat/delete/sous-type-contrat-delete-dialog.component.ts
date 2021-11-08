import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISousTypeContrat } from '../sous-type-contrat.model';
import { SousTypeContratService } from '../service/sous-type-contrat.service';

@Component({
  templateUrl: './sous-type-contrat-delete-dialog.component.html',
})
export class SousTypeContratDeleteDialogComponent {
  sousTypeContrat?: ISousTypeContrat;

  constructor(protected sousTypeContratService: SousTypeContratService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sousTypeContratService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
