import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeIdentite } from '../type-identite.model';
import { TypeIdentiteService } from '../service/type-identite.service';

@Component({
  templateUrl: './type-identite-delete-dialog.component.html',
})
export class TypeIdentiteDeleteDialogComponent {
  typeIdentite?: ITypeIdentite;

  constructor(protected typeIdentiteService: TypeIdentiteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeIdentiteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
