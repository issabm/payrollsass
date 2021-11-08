import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureAbsenceComponent } from './list/nature-absence.component';
import { NatureAbsenceDetailComponent } from './detail/nature-absence-detail.component';
import { NatureAbsenceUpdateComponent } from './update/nature-absence-update.component';
import { NatureAbsenceDeleteDialogComponent } from './delete/nature-absence-delete-dialog.component';
import { NatureAbsenceRoutingModule } from './route/nature-absence-routing.module';

@NgModule({
  imports: [SharedModule, NatureAbsenceRoutingModule],
  declarations: [NatureAbsenceComponent, NatureAbsenceDetailComponent, NatureAbsenceUpdateComponent, NatureAbsenceDeleteDialogComponent],
  entryComponents: [NatureAbsenceDeleteDialogComponent],
})
export class NatureAbsenceModule {}
