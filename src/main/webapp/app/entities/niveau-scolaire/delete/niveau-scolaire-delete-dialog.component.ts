import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INiveauScolaire } from '../niveau-scolaire.model';
import { NiveauScolaireService } from '../service/niveau-scolaire.service';

@Component({
  templateUrl: './niveau-scolaire-delete-dialog.component.html',
})
export class NiveauScolaireDeleteDialogComponent {
  niveauScolaire?: INiveauScolaire;

  constructor(protected niveauScolaireService: NiveauScolaireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.niveauScolaireService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
