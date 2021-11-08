import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserLogComponent } from '../list/user-log.component';
import { UserLogDetailComponent } from '../detail/user-log-detail.component';
import { UserLogUpdateComponent } from '../update/user-log-update.component';
import { UserLogRoutingResolveService } from './user-log-routing-resolve.service';

const userLogRoute: Routes = [
  {
    path: '',
    component: UserLogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserLogDetailComponent,
    resolve: {
      userLog: UserLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserLogUpdateComponent,
    resolve: {
      userLog: UserLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserLogUpdateComponent,
    resolve: {
      userLog: UserLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userLogRoute)],
  exports: [RouterModule],
})
export class UserLogRoutingModule {}
