import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeInput } from '../mode-input.model';
import { ModeInputService } from '../service/mode-input.service';

@Component({
  templateUrl: './mode-input-delete-dialog.component.html',
})
export class ModeInputDeleteDialogComponent {
  modeInput?: IModeInput;

  constructor(protected modeInputService: ModeInputService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modeInputService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
