import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FrequenceComponent } from './list/frequence.component';
import { FrequenceDetailComponent } from './detail/frequence-detail.component';
import { FrequenceUpdateComponent } from './update/frequence-update.component';
import { FrequenceDeleteDialogComponent } from './delete/frequence-delete-dialog.component';
import { FrequenceRoutingModule } from './route/frequence-routing.module';

@NgModule({
  imports: [SharedModule, FrequenceRoutingModule],
  declarations: [FrequenceComponent, FrequenceDetailComponent, FrequenceUpdateComponent, FrequenceDeleteDialogComponent],
  entryComponents: [FrequenceDeleteDialogComponent],
})
export class FrequenceModule {}
