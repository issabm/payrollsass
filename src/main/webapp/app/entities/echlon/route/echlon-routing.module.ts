import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EchlonComponent } from '../list/echlon.component';
import { EchlonDetailComponent } from '../detail/echlon-detail.component';
import { EchlonUpdateComponent } from '../update/echlon-update.component';
import { EchlonRoutingResolveService } from './echlon-routing-resolve.service';

const echlonRoute: Routes = [
  {
    path: '',
    component: EchlonComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EchlonDetailComponent,
    resolve: {
      echlon: EchlonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EchlonUpdateComponent,
    resolve: {
      echlon: EchlonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EchlonUpdateComponent,
    resolve: {
      echlon: EchlonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(echlonRoute)],
  exports: [RouterModule],
})
export class EchlonRoutingModule {}
