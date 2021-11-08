import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureAdhesion } from '../nature-adhesion.model';
import { NatureAdhesionService } from '../service/nature-adhesion.service';

@Component({
  templateUrl: './nature-adhesion-delete-dialog.component.html',
})
export class NatureAdhesionDeleteDialogComponent {
  natureAdhesion?: INatureAdhesion;

  constructor(protected natureAdhesionService: NatureAdhesionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureAdhesionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
