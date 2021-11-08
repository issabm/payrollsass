import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PayrollInfoComponent } from '../list/payroll-info.component';
import { PayrollInfoDetailComponent } from '../detail/payroll-info-detail.component';
import { PayrollInfoUpdateComponent } from '../update/payroll-info-update.component';
import { PayrollInfoRoutingResolveService } from './payroll-info-routing-resolve.service';

const payrollInfoRoute: Routes = [
  {
    path: '',
    component: PayrollInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayrollInfoDetailComponent,
    resolve: {
      payrollInfo: PayrollInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayrollInfoUpdateComponent,
    resolve: {
      payrollInfo: PayrollInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayrollInfoUpdateComponent,
    resolve: {
      payrollInfo: PayrollInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(payrollInfoRoute)],
  exports: [RouterModule],
})
export class PayrollInfoRoutingModule {}
