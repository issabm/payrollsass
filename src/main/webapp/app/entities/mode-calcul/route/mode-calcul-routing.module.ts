import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModeCalculComponent } from '../list/mode-calcul.component';
import { ModeCalculDetailComponent } from '../detail/mode-calcul-detail.component';
import { ModeCalculUpdateComponent } from '../update/mode-calcul-update.component';
import { ModeCalculRoutingResolveService } from './mode-calcul-routing-resolve.service';

const modeCalculRoute: Routes = [
  {
    path: '',
    component: ModeCalculComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModeCalculDetailComponent,
    resolve: {
      modeCalcul: ModeCalculRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModeCalculUpdateComponent,
    resolve: {
      modeCalcul: ModeCalculRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModeCalculUpdateComponent,
    resolve: {
      modeCalcul: ModeCalculRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(modeCalculRoute)],
  exports: [RouterModule],
})
export class ModeCalculRoutingModule {}
