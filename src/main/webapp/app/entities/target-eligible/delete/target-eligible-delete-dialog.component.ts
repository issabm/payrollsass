import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITargetEligible } from '../target-eligible.model';
import { TargetEligibleService } from '../service/target-eligible.service';

@Component({
  templateUrl: './target-eligible-delete-dialog.component.html',
})
export class TargetEligibleDeleteDialogComponent {
  targetEligible?: ITargetEligible;

  constructor(protected targetEligibleService: TargetEligibleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.targetEligibleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
