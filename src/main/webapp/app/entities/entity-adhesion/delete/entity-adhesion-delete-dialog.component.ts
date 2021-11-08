import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityAdhesion } from '../entity-adhesion.model';
import { EntityAdhesionService } from '../service/entity-adhesion.service';

@Component({
  templateUrl: './entity-adhesion-delete-dialog.component.html',
})
export class EntityAdhesionDeleteDialogComponent {
  entityAdhesion?: IEntityAdhesion;

  constructor(protected entityAdhesionService: EntityAdhesionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entityAdhesionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
