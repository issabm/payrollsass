import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SoldeAbsenceComponent } from '../list/solde-absence.component';
import { SoldeAbsenceDetailComponent } from '../detail/solde-absence-detail.component';
import { SoldeAbsenceUpdateComponent } from '../update/solde-absence-update.component';
import { SoldeAbsenceRoutingResolveService } from './solde-absence-routing-resolve.service';

const soldeAbsenceRoute: Routes = [
  {
    path: '',
    component: SoldeAbsenceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SoldeAbsenceDetailComponent,
    resolve: {
      soldeAbsence: SoldeAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SoldeAbsenceUpdateComponent,
    resolve: {
      soldeAbsence: SoldeAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SoldeAbsenceUpdateComponent,
    resolve: {
      soldeAbsence: SoldeAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(soldeAbsenceRoute)],
  exports: [RouterModule],
})
export class SoldeAbsenceRoutingModule {}
