import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureAbsenceComponent } from '../list/nature-absence.component';
import { NatureAbsenceDetailComponent } from '../detail/nature-absence-detail.component';
import { NatureAbsenceUpdateComponent } from '../update/nature-absence-update.component';
import { NatureAbsenceRoutingResolveService } from './nature-absence-routing-resolve.service';

const natureAbsenceRoute: Routes = [
  {
    path: '',
    component: NatureAbsenceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureAbsenceDetailComponent,
    resolve: {
      natureAbsence: NatureAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureAbsenceUpdateComponent,
    resolve: {
      natureAbsence: NatureAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureAbsenceUpdateComponent,
    resolve: {
      natureAbsence: NatureAbsenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureAbsenceRoute)],
  exports: [RouterModule],
})
export class NatureAbsenceRoutingModule {}
