import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagementResourceComponent } from '../list/management-resource.component';
import { ManagementResourceDetailComponent } from '../detail/management-resource-detail.component';
import { ManagementResourceUpdateComponent } from '../update/management-resource-update.component';
import { ManagementResourceRoutingResolveService } from './management-resource-routing-resolve.service';

const managementResourceRoute: Routes = [
  {
    path: '',
    component: ManagementResourceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagementResourceDetailComponent,
    resolve: {
      managementResource: ManagementResourceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagementResourceUpdateComponent,
    resolve: {
      managementResource: ManagementResourceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagementResourceUpdateComponent,
    resolve: {
      managementResource: ManagementResourceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(managementResourceRoute)],
  exports: [RouterModule],
})
export class ManagementResourceRoutingModule {}
