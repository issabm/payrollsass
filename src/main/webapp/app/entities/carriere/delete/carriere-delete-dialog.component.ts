import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarriere } from '../carriere.model';
import { CarriereService } from '../service/carriere.service';

@Component({
  templateUrl: './carriere-delete-dialog.component.html',
})
export class CarriereDeleteDialogComponent {
  carriere?: ICarriere;

  constructor(protected carriereService: CarriereService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carriereService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
