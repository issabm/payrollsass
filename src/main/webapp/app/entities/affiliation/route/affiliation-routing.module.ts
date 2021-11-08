import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AffiliationComponent } from '../list/affiliation.component';
import { AffiliationDetailComponent } from '../detail/affiliation-detail.component';
import { AffiliationUpdateComponent } from '../update/affiliation-update.component';
import { AffiliationRoutingResolveService } from './affiliation-routing-resolve.service';

const affiliationRoute: Routes = [
  {
    path: '',
    component: AffiliationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AffiliationDetailComponent,
    resolve: {
      affiliation: AffiliationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AffiliationUpdateComponent,
    resolve: {
      affiliation: AffiliationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AffiliationUpdateComponent,
    resolve: {
      affiliation: AffiliationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(affiliationRoute)],
  exports: [RouterModule],
})
export class AffiliationRoutingModule {}
