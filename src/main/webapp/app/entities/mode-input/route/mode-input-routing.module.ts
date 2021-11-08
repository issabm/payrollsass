import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModeInputComponent } from '../list/mode-input.component';
import { ModeInputDetailComponent } from '../detail/mode-input-detail.component';
import { ModeInputUpdateComponent } from '../update/mode-input-update.component';
import { ModeInputRoutingResolveService } from './mode-input-routing-resolve.service';

const modeInputRoute: Routes = [
  {
    path: '',
    component: ModeInputComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModeInputDetailComponent,
    resolve: {
      modeInput: ModeInputRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModeInputUpdateComponent,
    resolve: {
      modeInput: ModeInputRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModeInputUpdateComponent,
    resolve: {
      modeInput: ModeInputRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(modeInputRoute)],
  exports: [RouterModule],
})
export class ModeInputRoutingModule {}
