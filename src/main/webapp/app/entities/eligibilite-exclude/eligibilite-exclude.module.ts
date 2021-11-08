import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EligibiliteExcludeComponent } from './list/eligibilite-exclude.component';
import { EligibiliteExcludeDetailComponent } from './detail/eligibilite-exclude-detail.component';
import { EligibiliteExcludeUpdateComponent } from './update/eligibilite-exclude-update.component';
import { EligibiliteExcludeDeleteDialogComponent } from './delete/eligibilite-exclude-delete-dialog.component';
import { EligibiliteExcludeRoutingModule } from './route/eligibilite-exclude-routing.module';

@NgModule({
  imports: [SharedModule, EligibiliteExcludeRoutingModule],
  declarations: [
    EligibiliteExcludeComponent,
    EligibiliteExcludeDetailComponent,
    EligibiliteExcludeUpdateComponent,
    EligibiliteExcludeDeleteDialogComponent,
  ],
  entryComponents: [EligibiliteExcludeDeleteDialogComponent],
})
export class EligibiliteExcludeModule {}
