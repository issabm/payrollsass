import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEligibiliteExclude } from '../eligibilite-exclude.model';
import { EligibiliteExcludeService } from '../service/eligibilite-exclude.service';

@Component({
  templateUrl: './eligibilite-exclude-delete-dialog.component.html',
})
export class EligibiliteExcludeDeleteDialogComponent {
  eligibiliteExclude?: IEligibiliteExclude;

  constructor(protected eligibiliteExcludeService: EligibiliteExcludeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eligibiliteExcludeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
