import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SoldeAbsenceComponent } from './list/solde-absence.component';
import { SoldeAbsenceDetailComponent } from './detail/solde-absence-detail.component';
import { SoldeAbsenceUpdateComponent } from './update/solde-absence-update.component';
import { SoldeAbsenceDeleteDialogComponent } from './delete/solde-absence-delete-dialog.component';
import { SoldeAbsenceRoutingModule } from './route/solde-absence-routing.module';

@NgModule({
  imports: [SharedModule, SoldeAbsenceRoutingModule],
  declarations: [SoldeAbsenceComponent, SoldeAbsenceDetailComponent, SoldeAbsenceUpdateComponent, SoldeAbsenceDeleteDialogComponent],
  entryComponents: [SoldeAbsenceDeleteDialogComponent],
})
export class SoldeAbsenceModule {}
