import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityAdhesionComponent } from '../list/entity-adhesion.component';
import { EntityAdhesionDetailComponent } from '../detail/entity-adhesion-detail.component';
import { EntityAdhesionUpdateComponent } from '../update/entity-adhesion-update.component';
import { EntityAdhesionRoutingResolveService } from './entity-adhesion-routing-resolve.service';

const entityAdhesionRoute: Routes = [
  {
    path: '',
    component: EntityAdhesionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntityAdhesionDetailComponent,
    resolve: {
      entityAdhesion: EntityAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntityAdhesionUpdateComponent,
    resolve: {
      entityAdhesion: EntityAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntityAdhesionUpdateComponent,
    resolve: {
      entityAdhesion: EntityAdhesionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(entityAdhesionRoute)],
  exports: [RouterModule],
})
export class EntityAdhesionRoutingModule {}
