import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISexe } from '../sexe.model';
import { SexeService } from '../service/sexe.service';

@Component({
  templateUrl: './sexe-delete-dialog.component.html',
})
export class SexeDeleteDialogComponent {
  sexe?: ISexe;

  constructor(protected sexeService: SexeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sexeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
