import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlateInfoComponent } from './list/plate-info.component';
import { PlateInfoDetailComponent } from './detail/plate-info-detail.component';
import { PlateInfoUpdateComponent } from './update/plate-info-update.component';
import { PlateInfoDeleteDialogComponent } from './delete/plate-info-delete-dialog.component';
import { PlateInfoRoutingModule } from './route/plate-info-routing.module';

@NgModule({
  imports: [SharedModule, PlateInfoRoutingModule],
  declarations: [PlateInfoComponent, PlateInfoDetailComponent, PlateInfoUpdateComponent, PlateInfoDeleteDialogComponent],
  entryComponents: [PlateInfoDeleteDialogComponent],
})
export class PlateInfoModule {}
