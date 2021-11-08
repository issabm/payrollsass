import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMouvementPaie } from '../mouvement-paie.model';
import { MouvementPaieService } from '../service/mouvement-paie.service';

@Component({
  templateUrl: './mouvement-paie-delete-dialog.component.html',
})
export class MouvementPaieDeleteDialogComponent {
  mouvementPaie?: IMouvementPaie;

  constructor(protected mouvementPaieService: MouvementPaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mouvementPaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
