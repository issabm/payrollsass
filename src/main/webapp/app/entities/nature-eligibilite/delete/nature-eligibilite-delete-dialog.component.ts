import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureEligibilite } from '../nature-eligibilite.model';
import { NatureEligibiliteService } from '../service/nature-eligibilite.service';

@Component({
  templateUrl: './nature-eligibilite-delete-dialog.component.html',
})
export class NatureEligibiliteDeleteDialogComponent {
  natureEligibilite?: INatureEligibilite;

  constructor(protected natureEligibiliteService: NatureEligibiliteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureEligibiliteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
