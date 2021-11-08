import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SexeComponent } from '../list/sexe.component';
import { SexeDetailComponent } from '../detail/sexe-detail.component';
import { SexeUpdateComponent } from '../update/sexe-update.component';
import { SexeRoutingResolveService } from './sexe-routing-resolve.service';

const sexeRoute: Routes = [
  {
    path: '',
    component: SexeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SexeDetailComponent,
    resolve: {
      sexe: SexeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SexeUpdateComponent,
    resolve: {
      sexe: SexeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SexeUpdateComponent,
    resolve: {
      sexe: SexeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sexeRoute)],
  exports: [RouterModule],
})
export class SexeRoutingModule {}
