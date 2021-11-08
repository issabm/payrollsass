import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EchlonComponent } from './list/echlon.component';
import { EchlonDetailComponent } from './detail/echlon-detail.component';
import { EchlonUpdateComponent } from './update/echlon-update.component';
import { EchlonDeleteDialogComponent } from './delete/echlon-delete-dialog.component';
import { EchlonRoutingModule } from './route/echlon-routing.module';

@NgModule({
  imports: [SharedModule, EchlonRoutingModule],
  declarations: [EchlonComponent, EchlonDetailComponent, EchlonUpdateComponent, EchlonDeleteDialogComponent],
  entryComponents: [EchlonDeleteDialogComponent],
})
export class EchlonModule {}
