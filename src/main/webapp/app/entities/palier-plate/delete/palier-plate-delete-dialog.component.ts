import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPalierPlate } from '../palier-plate.model';
import { PalierPlateService } from '../service/palier-plate.service';

@Component({
  templateUrl: './palier-plate-delete-dialog.component.html',
})
export class PalierPlateDeleteDialogComponent {
  palierPlate?: IPalierPlate;

  constructor(protected palierPlateService: PalierPlateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.palierPlateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
