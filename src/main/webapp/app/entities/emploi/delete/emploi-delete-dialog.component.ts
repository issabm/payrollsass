import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploi } from '../emploi.model';
import { EmploiService } from '../service/emploi.service';

@Component({
  templateUrl: './emploi-delete-dialog.component.html',
})
export class EmploiDeleteDialogComponent {
  emploi?: IEmploi;

  constructor(protected emploiService: EmploiService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emploiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
