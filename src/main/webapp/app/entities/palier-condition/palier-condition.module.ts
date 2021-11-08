import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PalierConditionComponent } from './list/palier-condition.component';
import { PalierConditionDetailComponent } from './detail/palier-condition-detail.component';
import { PalierConditionUpdateComponent } from './update/palier-condition-update.component';
import { PalierConditionDeleteDialogComponent } from './delete/palier-condition-delete-dialog.component';
import { PalierConditionRoutingModule } from './route/palier-condition-routing.module';

@NgModule({
  imports: [SharedModule, PalierConditionRoutingModule],
  declarations: [
    PalierConditionComponent,
    PalierConditionDetailComponent,
    PalierConditionUpdateComponent,
    PalierConditionDeleteDialogComponent,
  ],
  entryComponents: [PalierConditionDeleteDialogComponent],
})
export class PalierConditionModule {}
