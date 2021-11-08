import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureConfigComponent } from '../list/nature-config.component';
import { NatureConfigDetailComponent } from '../detail/nature-config-detail.component';
import { NatureConfigUpdateComponent } from '../update/nature-config-update.component';
import { NatureConfigRoutingResolveService } from './nature-config-routing-resolve.service';

const natureConfigRoute: Routes = [
  {
    path: '',
    component: NatureConfigComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureConfigDetailComponent,
    resolve: {
      natureConfig: NatureConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureConfigUpdateComponent,
    resolve: {
      natureConfig: NatureConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureConfigUpdateComponent,
    resolve: {
      natureConfig: NatureConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureConfigRoute)],
  exports: [RouterModule],
})
export class NatureConfigRoutingModule {}
