import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagementResourceFunComponent } from '../list/management-resource-fun.component';
import { ManagementResourceFunDetailComponent } from '../detail/management-resource-fun-detail.component';
import { ManagementResourceFunUpdateComponent } from '../update/management-resource-fun-update.component';
import { ManagementResourceFunRoutingResolveService } from './management-resource-fun-routing-resolve.service';

const managementResourceFunRoute: Routes = [
  {
    path: '',
    component: ManagementResourceFunComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagementResourceFunDetailComponent,
    resolve: {
      managementResourceFun: ManagementResourceFunRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagementResourceFunUpdateComponent,
    resolve: {
      managementResourceFun: ManagementResourceFunRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagementResourceFunUpdateComponent,
    resolve: {
      managementResourceFun: ManagementResourceFunRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(managementResourceFunRoute)],
  exports: [RouterModule],
})
export class ManagementResourceFunRoutingModule {}
