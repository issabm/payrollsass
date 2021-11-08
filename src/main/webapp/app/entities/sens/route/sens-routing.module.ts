import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SensComponent } from '../list/sens.component';
import { SensDetailComponent } from '../detail/sens-detail.component';
import { SensUpdateComponent } from '../update/sens-update.component';
import { SensRoutingResolveService } from './sens-routing-resolve.service';

const sensRoute: Routes = [
  {
    path: '',
    component: SensComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SensDetailComponent,
    resolve: {
      sens: SensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SensUpdateComponent,
    resolve: {
      sens: SensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SensUpdateComponent,
    resolve: {
      sens: SensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sensRoute)],
  exports: [RouterModule],
})
export class SensRoutingModule {}
