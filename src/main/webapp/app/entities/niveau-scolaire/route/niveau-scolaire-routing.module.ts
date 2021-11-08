import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NiveauScolaireComponent } from '../list/niveau-scolaire.component';
import { NiveauScolaireDetailComponent } from '../detail/niveau-scolaire-detail.component';
import { NiveauScolaireUpdateComponent } from '../update/niveau-scolaire-update.component';
import { NiveauScolaireRoutingResolveService } from './niveau-scolaire-routing-resolve.service';

const niveauScolaireRoute: Routes = [
  {
    path: '',
    component: NiveauScolaireComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NiveauScolaireDetailComponent,
    resolve: {
      niveauScolaire: NiveauScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NiveauScolaireUpdateComponent,
    resolve: {
      niveauScolaire: NiveauScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NiveauScolaireUpdateComponent,
    resolve: {
      niveauScolaire: NiveauScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(niveauScolaireRoute)],
  exports: [RouterModule],
})
export class NiveauScolaireRoutingModule {}
