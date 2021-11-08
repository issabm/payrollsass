import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PalierPlateComponent } from './list/palier-plate.component';
import { PalierPlateDetailComponent } from './detail/palier-plate-detail.component';
import { PalierPlateUpdateComponent } from './update/palier-plate-update.component';
import { PalierPlateDeleteDialogComponent } from './delete/palier-plate-delete-dialog.component';
import { PalierPlateRoutingModule } from './route/palier-plate-routing.module';

@NgModule({
  imports: [SharedModule, PalierPlateRoutingModule],
  declarations: [PalierPlateComponent, PalierPlateDetailComponent, PalierPlateUpdateComponent, PalierPlateDeleteDialogComponent],
  entryComponents: [PalierPlateDeleteDialogComponent],
})
export class PalierPlateModule {}
