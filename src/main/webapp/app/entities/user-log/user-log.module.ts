import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserLogComponent } from './list/user-log.component';
import { UserLogDetailComponent } from './detail/user-log-detail.component';
import { UserLogUpdateComponent } from './update/user-log-update.component';
import { UserLogDeleteDialogComponent } from './delete/user-log-delete-dialog.component';
import { UserLogRoutingModule } from './route/user-log-routing.module';

@NgModule({
  imports: [SharedModule, UserLogRoutingModule],
  declarations: [UserLogComponent, UserLogDetailComponent, UserLogUpdateComponent, UserLogDeleteDialogComponent],
  entryComponents: [UserLogDeleteDialogComponent],
})
export class UserLogModule {}
