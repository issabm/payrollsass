import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EligibiliteComponent } from '../list/eligibilite.component';
import { EligibiliteDetailComponent } from '../detail/eligibilite-detail.component';
import { EligibiliteUpdateComponent } from '../update/eligibilite-update.component';
import { EligibiliteRoutingResolveService } from './eligibilite-routing-resolve.service';

const eligibiliteRoute: Routes = [
  {
    path: '',
    component: EligibiliteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EligibiliteDetailComponent,
    resolve: {
      eligibilite: EligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EligibiliteUpdateComponent,
    resolve: {
      eligibilite: EligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EligibiliteUpdateComponent,
    resolve: {
      eligibilite: EligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eligibiliteRoute)],
  exports: [RouterModule],
})
export class EligibiliteRoutingModule {}
