import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DeviseComponent } from './list/devise.component';
import { DeviseDetailComponent } from './detail/devise-detail.component';
import { DeviseUpdateComponent } from './update/devise-update.component';
import { DeviseDeleteDialogComponent } from './delete/devise-delete-dialog.component';
import { DeviseRoutingModule } from './route/devise-routing.module';

@NgModule({
  imports: [SharedModule, DeviseRoutingModule],
  declarations: [DeviseComponent, DeviseDetailComponent, DeviseUpdateComponent, DeviseDeleteDialogComponent],
  entryComponents: [DeviseDeleteDialogComponent],
})
export class DeviseModule {}
