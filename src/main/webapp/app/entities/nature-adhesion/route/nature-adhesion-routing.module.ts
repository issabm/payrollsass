import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureAdhesionComponent } from '../list/nature-adhesion.component';
import { NatureAdhesionDetailComponent } from '../detail/nature-adhesion-detail.component';
import { NatureAdhesionUpdateComponent } from '../update/nature-adhesion-update.component';
import { NatureAdhesionRoutingResolveService } from './nature-adhesion-routing-resolve.service';

const natureAdhesionRoute: Routes = [
  {
    path: '',
    component: NatureAdhesionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureAdhesionDetailComponent,
    resolve: {
      natureAdhesion: NatureAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureAdhesionUpdateComponent,
    resolve: {
      natureAdhesion: NatureAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureAdhesionUpdateComponent,
    resolve: {
      natureAdhesion: NatureAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureAdhesionRoute)],
  exports: [RouterModule],
})
export class NatureAdhesionRoutingModule {}
