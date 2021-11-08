import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConcerne } from '../concerne.model';
import { ConcerneService } from '../service/concerne.service';

@Component({
  templateUrl: './concerne-delete-dialog.component.html',
})
export class ConcerneDeleteDialogComponent {
  concerne?: IConcerne;

  constructor(protected concerneService: ConcerneService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.concerneService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
