import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConjointComponent } from '../list/conjoint.component';
import { ConjointDetailComponent } from '../detail/conjoint-detail.component';
import { ConjointUpdateComponent } from '../update/conjoint-update.component';
import { ConjointRoutingResolveService } from './conjoint-routing-resolve.service';

const conjointRoute: Routes = [
  {
    path: '',
    component: ConjointComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConjointDetailComponent,
    resolve: {
      conjoint: ConjointRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConjointUpdateComponent,
    resolve: {
      conjoint: ConjointRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConjointUpdateComponent,
    resolve: {
      conjoint: ConjointRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(conjointRoute)],
  exports: [RouterModule],
})
export class ConjointRoutingModule {}
