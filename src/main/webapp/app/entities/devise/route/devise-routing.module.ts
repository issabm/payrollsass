import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DeviseComponent } from '../list/devise.component';
import { DeviseDetailComponent } from '../detail/devise-detail.component';
import { DeviseUpdateComponent } from '../update/devise-update.component';
import { DeviseRoutingResolveService } from './devise-routing-resolve.service';

const deviseRoute: Routes = [
  {
    path: '',
    component: DeviseComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviseDetailComponent,
    resolve: {
      devise: DeviseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviseUpdateComponent,
    resolve: {
      devise: DeviseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviseUpdateComponent,
    resolve: {
      devise: DeviseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(deviseRoute)],
  exports: [RouterModule],
})
export class DeviseRoutingModule {}
