import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureMvtPaieComponent } from '../list/nature-mvt-paie.component';
import { NatureMvtPaieDetailComponent } from '../detail/nature-mvt-paie-detail.component';
import { NatureMvtPaieUpdateComponent } from '../update/nature-mvt-paie-update.component';
import { NatureMvtPaieRoutingResolveService } from './nature-mvt-paie-routing-resolve.service';

const natureMvtPaieRoute: Routes = [
  {
    path: '',
    component: NatureMvtPaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureMvtPaieDetailComponent,
    resolve: {
      natureMvtPaie: NatureMvtPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureMvtPaieUpdateComponent,
    resolve: {
      natureMvtPaie: NatureMvtPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureMvtPaieUpdateComponent,
    resolve: {
      natureMvtPaie: NatureMvtPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureMvtPaieRoute)],
  exports: [RouterModule],
})
export class NatureMvtPaieRoutingModule {}
