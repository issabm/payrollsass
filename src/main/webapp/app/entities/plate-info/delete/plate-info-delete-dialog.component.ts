import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlateInfo } from '../plate-info.model';
import { PlateInfoService } from '../service/plate-info.service';

@Component({
  templateUrl: './plate-info-delete-dialog.component.html',
})
export class PlateInfoDeleteDialogComponent {
  plateInfo?: IPlateInfo;

  constructor(protected plateInfoService: PlateInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plateInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
