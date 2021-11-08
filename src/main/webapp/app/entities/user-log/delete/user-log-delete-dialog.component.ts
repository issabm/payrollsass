import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserLog } from '../user-log.model';
import { UserLogService } from '../service/user-log.service';

@Component({
  templateUrl: './user-log-delete-dialog.component.html',
})
export class UserLogDeleteDialogComponent {
  userLog?: IUserLog;

  constructor(protected userLogService: UserLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
