import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRebrique } from '../rebrique.model';
import { RebriqueService } from '../service/rebrique.service';

@Component({
  templateUrl: './rebrique-delete-dialog.component.html',
})
export class RebriqueDeleteDialogComponent {
  rebrique?: IRebrique;

  constructor(protected rebriqueService: RebriqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rebriqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
