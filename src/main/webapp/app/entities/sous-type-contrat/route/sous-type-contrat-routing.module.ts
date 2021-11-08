import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SousTypeContratComponent } from '../list/sous-type-contrat.component';
import { SousTypeContratDetailComponent } from '../detail/sous-type-contrat-detail.component';
import { SousTypeContratUpdateComponent } from '../update/sous-type-contrat-update.component';
import { SousTypeContratRoutingResolveService } from './sous-type-contrat-routing-resolve.service';

const sousTypeContratRoute: Routes = [
  {
    path: '',
    component: SousTypeContratComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SousTypeContratDetailComponent,
    resolve: {
      sousTypeContrat: SousTypeContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SousTypeContratUpdateComponent,
    resolve: {
      sousTypeContrat: SousTypeContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SousTypeContratUpdateComponent,
    resolve: {
      sousTypeContrat: SousTypeContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sousTypeContratRoute)],
  exports: [RouterModule],
})
export class SousTypeContratRoutingModule {}
