import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureConfig } from '../nature-config.model';
import { NatureConfigService } from '../service/nature-config.service';

@Component({
  templateUrl: './nature-config-delete-dialog.component.html',
})
export class NatureConfigDeleteDialogComponent {
  natureConfig?: INatureConfig;

  constructor(protected natureConfigService: NatureConfigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureConfigService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
