import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RebriqueComponent } from '../list/rebrique.component';
import { RebriqueDetailComponent } from '../detail/rebrique-detail.component';
import { RebriqueUpdateComponent } from '../update/rebrique-update.component';
import { RebriqueRoutingResolveService } from './rebrique-routing-resolve.service';

const rebriqueRoute: Routes = [
  {
    path: '',
    component: RebriqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RebriqueDetailComponent,
    resolve: {
      rebrique: RebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RebriqueUpdateComponent,
    resolve: {
      rebrique: RebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RebriqueUpdateComponent,
    resolve: {
      rebrique: RebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rebriqueRoute)],
  exports: [RouterModule],
})
export class RebriqueRoutingModule {}
