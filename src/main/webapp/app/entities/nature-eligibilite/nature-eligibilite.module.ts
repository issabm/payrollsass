import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureEligibiliteComponent } from './list/nature-eligibilite.component';
import { NatureEligibiliteDetailComponent } from './detail/nature-eligibilite-detail.component';
import { NatureEligibiliteUpdateComponent } from './update/nature-eligibilite-update.component';
import { NatureEligibiliteDeleteDialogComponent } from './delete/nature-eligibilite-delete-dialog.component';
import { NatureEligibiliteRoutingModule } from './route/nature-eligibilite-routing.module';

@NgModule({
  imports: [SharedModule, NatureEligibiliteRoutingModule],
  declarations: [
    NatureEligibiliteComponent,
    NatureEligibiliteDetailComponent,
    NatureEligibiliteUpdateComponent,
    NatureEligibiliteDeleteDialogComponent,
  ],
  entryComponents: [NatureEligibiliteDeleteDialogComponent],
})
export class NatureEligibiliteModule {}
