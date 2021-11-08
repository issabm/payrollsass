import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AffiliationComponent } from './list/affiliation.component';
import { AffiliationDetailComponent } from './detail/affiliation-detail.component';
import { AffiliationUpdateComponent } from './update/affiliation-update.component';
import { AffiliationDeleteDialogComponent } from './delete/affiliation-delete-dialog.component';
import { AffiliationRoutingModule } from './route/affiliation-routing.module';

@NgModule({
  imports: [SharedModule, AffiliationRoutingModule],
  declarations: [AffiliationComponent, AffiliationDetailComponent, AffiliationUpdateComponent, AffiliationDeleteDialogComponent],
  entryComponents: [AffiliationDeleteDialogComponent],
})
export class AffiliationModule {}
