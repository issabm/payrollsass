import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeCalcul } from '../mode-calcul.model';
import { ModeCalculService } from '../service/mode-calcul.service';

@Component({
  templateUrl: './mode-calcul-delete-dialog.component.html',
})
export class ModeCalculDeleteDialogComponent {
  modeCalcul?: IModeCalcul;

  constructor(protected modeCalculService: ModeCalculService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modeCalculService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
