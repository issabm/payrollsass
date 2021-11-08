import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EligibiliteComponent } from './list/eligibilite.component';
import { EligibiliteDetailComponent } from './detail/eligibilite-detail.component';
import { EligibiliteUpdateComponent } from './update/eligibilite-update.component';
import { EligibiliteDeleteDialogComponent } from './delete/eligibilite-delete-dialog.component';
import { EligibiliteRoutingModule } from './route/eligibilite-routing.module';

@NgModule({
  imports: [SharedModule, EligibiliteRoutingModule],
  declarations: [EligibiliteComponent, EligibiliteDetailComponent, EligibiliteUpdateComponent, EligibiliteDeleteDialogComponent],
  entryComponents: [EligibiliteDeleteDialogComponent],
})
export class EligibiliteModule {}
