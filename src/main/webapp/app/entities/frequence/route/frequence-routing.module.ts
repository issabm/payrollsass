import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FrequenceComponent } from '../list/frequence.component';
import { FrequenceDetailComponent } from '../detail/frequence-detail.component';
import { FrequenceUpdateComponent } from '../update/frequence-update.component';
import { FrequenceRoutingResolveService } from './frequence-routing-resolve.service';

const frequenceRoute: Routes = [
  {
    path: '',
    component: FrequenceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FrequenceDetailComponent,
    resolve: {
      frequence: FrequenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FrequenceUpdateComponent,
    resolve: {
      frequence: FrequenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FrequenceUpdateComponent,
    resolve: {
      frequence: FrequenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(frequenceRoute)],
  exports: [RouterModule],
})
export class FrequenceRoutingModule {}
