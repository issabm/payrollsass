import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConjoint } from '../conjoint.model';
import { ConjointService } from '../service/conjoint.service';

@Component({
  templateUrl: './conjoint-delete-dialog.component.html',
})
export class ConjointDeleteDialogComponent {
  conjoint?: IConjoint;

  constructor(protected conjointService: ConjointService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conjointService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
