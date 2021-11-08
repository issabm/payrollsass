import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureMvtPaie } from '../nature-mvt-paie.model';
import { NatureMvtPaieService } from '../service/nature-mvt-paie.service';

@Component({
  templateUrl: './nature-mvt-paie-delete-dialog.component.html',
})
export class NatureMvtPaieDeleteDialogComponent {
  natureMvtPaie?: INatureMvtPaie;

  constructor(protected natureMvtPaieService: NatureMvtPaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureMvtPaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
