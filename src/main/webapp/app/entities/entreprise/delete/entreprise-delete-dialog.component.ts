import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';

@Component({
  templateUrl: './entreprise-delete-dialog.component.html',
})
export class EntrepriseDeleteDialogComponent {
  entreprise?: IEntreprise;

  constructor(protected entrepriseService: EntrepriseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entrepriseService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
