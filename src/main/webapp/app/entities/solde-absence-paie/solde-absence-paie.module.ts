import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SoldeAbsencePaieComponent } from './list/solde-absence-paie.component';
import { SoldeAbsencePaieDetailComponent } from './detail/solde-absence-paie-detail.component';
import { SoldeAbsencePaieUpdateComponent } from './update/solde-absence-paie-update.component';
import { SoldeAbsencePaieDeleteDialogComponent } from './delete/solde-absence-paie-delete-dialog.component';
import { SoldeAbsencePaieRoutingModule } from './route/solde-absence-paie-routing.module';

@NgModule({
  imports: [SharedModule, SoldeAbsencePaieRoutingModule],
  declarations: [
    SoldeAbsencePaieComponent,
    SoldeAbsencePaieDetailComponent,
    SoldeAbsencePaieUpdateComponent,
    SoldeAbsencePaieDeleteDialogComponent,
  ],
  entryComponents: [SoldeAbsencePaieDeleteDialogComponent],
})
export class SoldeAbsencePaieModule {}
