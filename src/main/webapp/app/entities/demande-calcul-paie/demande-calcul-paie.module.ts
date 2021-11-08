import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeCalculPaieComponent } from './list/demande-calcul-paie.component';
import { DemandeCalculPaieDetailComponent } from './detail/demande-calcul-paie-detail.component';
import { DemandeCalculPaieUpdateComponent } from './update/demande-calcul-paie-update.component';
import { DemandeCalculPaieDeleteDialogComponent } from './delete/demande-calcul-paie-delete-dialog.component';
import { DemandeCalculPaieRoutingModule } from './route/demande-calcul-paie-routing.module';

@NgModule({
  imports: [SharedModule, DemandeCalculPaieRoutingModule],
  declarations: [
    DemandeCalculPaieComponent,
    DemandeCalculPaieDetailComponent,
    DemandeCalculPaieUpdateComponent,
    DemandeCalculPaieDeleteDialogComponent,
  ],
  entryComponents: [DemandeCalculPaieDeleteDialogComponent],
})
export class DemandeCalculPaieModule {}
