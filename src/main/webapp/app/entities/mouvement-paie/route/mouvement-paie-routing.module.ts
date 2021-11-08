import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MouvementPaieComponent } from '../list/mouvement-paie.component';
import { MouvementPaieDetailComponent } from '../detail/mouvement-paie-detail.component';
import { MouvementPaieUpdateComponent } from '../update/mouvement-paie-update.component';
import { MouvementPaieRoutingResolveService } from './mouvement-paie-routing-resolve.service';

const mouvementPaieRoute: Routes = [
  {
    path: '',
    component: MouvementPaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MouvementPaieDetailComponent,
    resolve: {
      mouvementPaie: MouvementPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MouvementPaieUpdateComponent,
    resolve: {
      mouvementPaie: MouvementPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MouvementPaieUpdateComponent,
    resolve: {
      mouvementPaie: MouvementPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mouvementPaieRoute)],
  exports: [RouterModule],
})
export class MouvementPaieRoutingModule {}
