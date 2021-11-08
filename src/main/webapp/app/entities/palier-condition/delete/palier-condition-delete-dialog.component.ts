import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPalierCondition } from '../palier-condition.model';
import { PalierConditionService } from '../service/palier-condition.service';

@Component({
  templateUrl: './palier-condition-delete-dialog.component.html',
})
export class PalierConditionDeleteDialogComponent {
  palierCondition?: IPalierCondition;

  constructor(protected palierConditionService: PalierConditionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.palierConditionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
