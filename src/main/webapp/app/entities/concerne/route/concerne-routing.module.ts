import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConcerneComponent } from '../list/concerne.component';
import { ConcerneDetailComponent } from '../detail/concerne-detail.component';
import { ConcerneUpdateComponent } from '../update/concerne-update.component';
import { ConcerneRoutingResolveService } from './concerne-routing-resolve.service';

const concerneRoute: Routes = [
  {
    path: '',
    component: ConcerneComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConcerneDetailComponent,
    resolve: {
      concerne: ConcerneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConcerneUpdateComponent,
    resolve: {
      concerne: ConcerneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConcerneUpdateComponent,
    resolve: {
      concerne: ConcerneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(concerneRoute)],
  exports: [RouterModule],
})
export class ConcerneRoutingModule {}
