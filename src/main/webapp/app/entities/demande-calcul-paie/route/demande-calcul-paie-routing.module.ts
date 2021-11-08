import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeCalculPaieComponent } from '../list/demande-calcul-paie.component';
import { DemandeCalculPaieDetailComponent } from '../detail/demande-calcul-paie-detail.component';
import { DemandeCalculPaieUpdateComponent } from '../update/demande-calcul-paie-update.component';
import { DemandeCalculPaieRoutingResolveService } from './demande-calcul-paie-routing-resolve.service';

const demandeCalculPaieRoute: Routes = [
  {
    path: '',
    component: DemandeCalculPaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeCalculPaieDetailComponent,
    resolve: {
      demandeCalculPaie: DemandeCalculPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeCalculPaieUpdateComponent,
    resolve: {
      demandeCalculPaie: DemandeCalculPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeCalculPaieUpdateComponent,
    resolve: {
      demandeCalculPaie: DemandeCalculPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeCalculPaieRoute)],
  exports: [RouterModule],
})
export class DemandeCalculPaieRoutingModule {}
