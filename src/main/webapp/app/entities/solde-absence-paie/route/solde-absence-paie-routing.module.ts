import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SoldeAbsencePaieComponent } from '../list/solde-absence-paie.component';
import { SoldeAbsencePaieDetailComponent } from '../detail/solde-absence-paie-detail.component';
import { SoldeAbsencePaieUpdateComponent } from '../update/solde-absence-paie-update.component';
import { SoldeAbsencePaieRoutingResolveService } from './solde-absence-paie-routing-resolve.service';

const soldeAbsencePaieRoute: Routes = [
  {
    path: '',
    component: SoldeAbsencePaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SoldeAbsencePaieDetailComponent,
    resolve: {
      soldeAbsencePaie: SoldeAbsencePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SoldeAbsencePaieUpdateComponent,
    resolve: {
      soldeAbsencePaie: SoldeAbsencePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SoldeAbsencePaieUpdateComponent,
    resolve: {
      soldeAbsencePaie: SoldeAbsencePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(soldeAbsencePaieRoute)],
  exports: [RouterModule],
})
export class SoldeAbsencePaieRoutingModule {}
