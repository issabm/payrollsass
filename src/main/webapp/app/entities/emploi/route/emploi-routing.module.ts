import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmploiComponent } from '../list/emploi.component';
import { EmploiDetailComponent } from '../detail/emploi-detail.component';
import { EmploiUpdateComponent } from '../update/emploi-update.component';
import { EmploiRoutingResolveService } from './emploi-routing-resolve.service';

const emploiRoute: Routes = [
  {
    path: '',
    component: EmploiComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmploiDetailComponent,
    resolve: {
      emploi: EmploiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmploiUpdateComponent,
    resolve: {
      emploi: EmploiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmploiUpdateComponent,
    resolve: {
      emploi: EmploiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emploiRoute)],
  exports: [RouterModule],
})
export class EmploiRoutingModule {}
