import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDevise } from '../devise.model';
import { DeviseService } from '../service/devise.service';

@Component({
  templateUrl: './devise-delete-dialog.component.html',
})
export class DeviseDeleteDialogComponent {
  devise?: IDevise;

  constructor(protected deviseService: DeviseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviseService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
