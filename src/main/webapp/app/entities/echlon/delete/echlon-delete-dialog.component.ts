import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEchlon } from '../echlon.model';
import { EchlonService } from '../service/echlon.service';

@Component({
  templateUrl: './echlon-delete-dialog.component.html',
})
export class EchlonDeleteDialogComponent {
  echlon?: IEchlon;

  constructor(protected echlonService: EchlonService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.echlonService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
