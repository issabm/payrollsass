import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureAbsence } from '../nature-absence.model';
import { NatureAbsenceService } from '../service/nature-absence.service';

@Component({
  templateUrl: './nature-absence-delete-dialog.component.html',
})
export class NatureAbsenceDeleteDialogComponent {
  natureAbsence?: INatureAbsence;

  constructor(protected natureAbsenceService: NatureAbsenceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureAbsenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
