import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PalierPlateComponent } from '../list/palier-plate.component';
import { PalierPlateDetailComponent } from '../detail/palier-plate-detail.component';
import { PalierPlateUpdateComponent } from '../update/palier-plate-update.component';
import { PalierPlateRoutingResolveService } from './palier-plate-routing-resolve.service';

const palierPlateRoute: Routes = [
  {
    path: '',
    component: PalierPlateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PalierPlateDetailComponent,
    resolve: {
      palierPlate: PalierPlateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PalierPlateUpdateComponent,
    resolve: {
      palierPlate: PalierPlateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PalierPlateUpdateComponent,
    resolve: {
      palierPlate: PalierPlateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(palierPlateRoute)],
  exports: [RouterModule],
})
export class PalierPlateRoutingModule {}
