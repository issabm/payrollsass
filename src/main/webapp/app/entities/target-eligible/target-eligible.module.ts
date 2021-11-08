import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TargetEligibleComponent } from './list/target-eligible.component';
import { TargetEligibleDetailComponent } from './detail/target-eligible-detail.component';
import { TargetEligibleUpdateComponent } from './update/target-eligible-update.component';
import { TargetEligibleDeleteDialogComponent } from './delete/target-eligible-delete-dialog.component';
import { TargetEligibleRoutingModule } from './route/target-eligible-routing.module';

@NgModule({
  imports: [SharedModule, TargetEligibleRoutingModule],
  declarations: [
    TargetEligibleComponent,
    TargetEligibleDetailComponent,
    TargetEligibleUpdateComponent,
    TargetEligibleDeleteDialogComponent,
  ],
  entryComponents: [TargetEligibleDeleteDialogComponent],
})
export class TargetEligibleModule {}
