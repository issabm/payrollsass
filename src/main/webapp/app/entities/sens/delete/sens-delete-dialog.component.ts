import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISens } from '../sens.model';
import { SensService } from '../service/sens.service';

@Component({
  templateUrl: './sens-delete-dialog.component.html',
})
export class SensDeleteDialogComponent {
  sens?: ISens;

  constructor(protected sensService: SensService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sensService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
