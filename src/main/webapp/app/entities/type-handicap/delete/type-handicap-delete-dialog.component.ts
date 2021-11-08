import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeHandicap } from '../type-handicap.model';
import { TypeHandicapService } from '../service/type-handicap.service';

@Component({
  templateUrl: './type-handicap-delete-dialog.component.html',
})
export class TypeHandicapDeleteDialogComponent {
  typeHandicap?: ITypeHandicap;

  constructor(protected typeHandicapService: TypeHandicapService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeHandicapService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
