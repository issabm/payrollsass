import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFrequence } from '../frequence.model';
import { FrequenceService } from '../service/frequence.service';

@Component({
  templateUrl: './frequence-delete-dialog.component.html',
})
export class FrequenceDeleteDialogComponent {
  frequence?: IFrequence;

  constructor(protected frequenceService: FrequenceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.frequenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
