import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PayrollInfoComponent } from './list/payroll-info.component';
import { PayrollInfoDetailComponent } from './detail/payroll-info-detail.component';
import { PayrollInfoUpdateComponent } from './update/payroll-info-update.component';
import { PayrollInfoDeleteDialogComponent } from './delete/payroll-info-delete-dialog.component';
import { PayrollInfoRoutingModule } from './route/payroll-info-routing.module';

@NgModule({
  imports: [SharedModule, PayrollInfoRoutingModule],
  declarations: [PayrollInfoComponent, PayrollInfoDetailComponent, PayrollInfoUpdateComponent, PayrollInfoDeleteDialogComponent],
  entryComponents: [PayrollInfoDeleteDialogComponent],
})
export class PayrollInfoModule {}
