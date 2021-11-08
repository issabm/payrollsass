import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeCalculPaie } from '../demande-calcul-paie.model';
import { DemandeCalculPaieService } from '../service/demande-calcul-paie.service';

@Component({
  templateUrl: './demande-calcul-paie-delete-dialog.component.html',
})
export class DemandeCalculPaieDeleteDialogComponent {
  demandeCalculPaie?: IDemandeCalculPaie;

  constructor(protected demandeCalculPaieService: DemandeCalculPaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeCalculPaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
