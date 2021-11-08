import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NiveauScolaireComponent } from './list/niveau-scolaire.component';
import { NiveauScolaireDetailComponent } from './detail/niveau-scolaire-detail.component';
import { NiveauScolaireUpdateComponent } from './update/niveau-scolaire-update.component';
import { NiveauScolaireDeleteDialogComponent } from './delete/niveau-scolaire-delete-dialog.component';
import { NiveauScolaireRoutingModule } from './route/niveau-scolaire-routing.module';

@NgModule({
  imports: [SharedModule, NiveauScolaireRoutingModule],
  declarations: [
    NiveauScolaireComponent,
    NiveauScolaireDetailComponent,
    NiveauScolaireUpdateComponent,
    NiveauScolaireDeleteDialogComponent,
  ],
  entryComponents: [NiveauScolaireDeleteDialogComponent],
})
export class NiveauScolaireModule {}
