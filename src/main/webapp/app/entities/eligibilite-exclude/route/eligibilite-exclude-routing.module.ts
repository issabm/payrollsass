import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EligibiliteExcludeComponent } from '../list/eligibilite-exclude.component';
import { EligibiliteExcludeDetailComponent } from '../detail/eligibilite-exclude-detail.component';
import { EligibiliteExcludeUpdateComponent } from '../update/eligibilite-exclude-update.component';
import { EligibiliteExcludeRoutingResolveService } from './eligibilite-exclude-routing-resolve.service';

const eligibiliteExcludeRoute: Routes = [
  {
    path: '',
    component: EligibiliteExcludeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EligibiliteExcludeDetailComponent,
    resolve: {
      eligibiliteExclude: EligibiliteExcludeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EligibiliteExcludeUpdateComponent,
    resolve: {
      eligibiliteExclude: EligibiliteExcludeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EligibiliteExcludeUpdateComponent,
    resolve: {
      eligibiliteExclude: EligibiliteExcludeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eligibiliteExcludeRoute)],
  exports: [RouterModule],
})
export class EligibiliteExcludeRoutingModule {}
