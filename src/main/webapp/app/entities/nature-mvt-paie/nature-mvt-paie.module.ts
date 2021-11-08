import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureMvtPaieComponent } from './list/nature-mvt-paie.component';
import { NatureMvtPaieDetailComponent } from './detail/nature-mvt-paie-detail.component';
import { NatureMvtPaieUpdateComponent } from './update/nature-mvt-paie-update.component';
import { NatureMvtPaieDeleteDialogComponent } from './delete/nature-mvt-paie-delete-dialog.component';
import { NatureMvtPaieRoutingModule } from './route/nature-mvt-paie-routing.module';

@NgModule({
  imports: [SharedModule, NatureMvtPaieRoutingModule],
  declarations: [NatureMvtPaieComponent, NatureMvtPaieDetailComponent, NatureMvtPaieUpdateComponent, NatureMvtPaieDeleteDialogComponent],
  entryComponents: [NatureMvtPaieDeleteDialogComponent],
})
export class NatureMvtPaieModule {}
