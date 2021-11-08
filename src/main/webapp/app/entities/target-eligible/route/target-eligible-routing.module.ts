import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TargetEligibleComponent } from '../list/target-eligible.component';
import { TargetEligibleDetailComponent } from '../detail/target-eligible-detail.component';
import { TargetEligibleUpdateComponent } from '../update/target-eligible-update.component';
import { TargetEligibleRoutingResolveService } from './target-eligible-routing-resolve.service';

const targetEligibleRoute: Routes = [
  {
    path: '',
    component: TargetEligibleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TargetEligibleDetailComponent,
    resolve: {
      targetEligible: TargetEligibleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TargetEligibleUpdateComponent,
    resolve: {
      targetEligible: TargetEligibleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TargetEligibleUpdateComponent,
    resolve: {
      targetEligible: TargetEligibleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(targetEligibleRoute)],
  exports: [RouterModule],
})
export class TargetEligibleRoutingModule {}
