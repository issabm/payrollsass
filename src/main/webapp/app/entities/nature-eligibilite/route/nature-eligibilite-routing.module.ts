import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureEligibiliteComponent } from '../list/nature-eligibilite.component';
import { NatureEligibiliteDetailComponent } from '../detail/nature-eligibilite-detail.component';
import { NatureEligibiliteUpdateComponent } from '../update/nature-eligibilite-update.component';
import { NatureEligibiliteRoutingResolveService } from './nature-eligibilite-routing-resolve.service';

const natureEligibiliteRoute: Routes = [
  {
    path: '',
    component: NatureEligibiliteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureEligibiliteDetailComponent,
    resolve: {
      natureEligibilite: NatureEligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureEligibiliteUpdateComponent,
    resolve: {
      natureEligibilite: NatureEligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureEligibiliteUpdateComponent,
    resolve: {
      natureEligibilite: NatureEligibiliteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureEligibiliteRoute)],
  exports: [RouterModule],
})
export class NatureEligibiliteRoutingModule {}
