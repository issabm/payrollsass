import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IdentiteComponent } from '../list/identite.component';
import { IdentiteDetailComponent } from '../detail/identite-detail.component';
import { IdentiteUpdateComponent } from '../update/identite-update.component';
import { IdentiteRoutingResolveService } from './identite-routing-resolve.service';

const identiteRoute: Routes = [
  {
    path: '',
    component: IdentiteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdentiteDetailComponent,
    resolve: {
      identite: IdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdentiteUpdateComponent,
    resolve: {
      identite: IdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdentiteUpdateComponent,
    resolve: {
      identite: IdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(identiteRoute)],
  exports: [RouterModule],
})
export class IdentiteRoutingModule {}
