import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdentite } from '../identite.model';
import { IdentiteService } from '../service/identite.service';

@Component({
  templateUrl: './identite-delete-dialog.component.html',
})
export class IdentiteDeleteDialogComponent {
  identite?: IIdentite;

  constructor(protected identiteService: IdentiteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.identiteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
