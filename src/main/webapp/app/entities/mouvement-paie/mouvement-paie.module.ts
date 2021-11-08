import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MouvementPaieComponent } from './list/mouvement-paie.component';
import { MouvementPaieDetailComponent } from './detail/mouvement-paie-detail.component';
import { MouvementPaieUpdateComponent } from './update/mouvement-paie-update.component';
import { MouvementPaieDeleteDialogComponent } from './delete/mouvement-paie-delete-dialog.component';
import { MouvementPaieRoutingModule } from './route/mouvement-paie-routing.module';

@NgModule({
  imports: [SharedModule, MouvementPaieRoutingModule],
  declarations: [MouvementPaieComponent, MouvementPaieDetailComponent, MouvementPaieUpdateComponent, MouvementPaieDeleteDialogComponent],
  entryComponents: [MouvementPaieDeleteDialogComponent],
})
export class MouvementPaieModule {}
