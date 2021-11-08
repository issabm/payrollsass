import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlateInfoComponent } from '../list/plate-info.component';
import { PlateInfoDetailComponent } from '../detail/plate-info-detail.component';
import { PlateInfoUpdateComponent } from '../update/plate-info-update.component';
import { PlateInfoRoutingResolveService } from './plate-info-routing-resolve.service';

const plateInfoRoute: Routes = [
  {
    path: '',
    component: PlateInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlateInfoDetailComponent,
    resolve: {
      plateInfo: PlateInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlateInfoUpdateComponent,
    resolve: {
      plateInfo: PlateInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlateInfoUpdateComponent,
    resolve: {
      plateInfo: PlateInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(plateInfoRoute)],
  exports: [RouterModule],
})
export class PlateInfoRoutingModule {}
