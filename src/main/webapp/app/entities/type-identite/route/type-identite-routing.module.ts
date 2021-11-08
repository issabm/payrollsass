import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeIdentiteComponent } from '../list/type-identite.component';
import { TypeIdentiteDetailComponent } from '../detail/type-identite-detail.component';
import { TypeIdentiteUpdateComponent } from '../update/type-identite-update.component';
import { TypeIdentiteRoutingResolveService } from './type-identite-routing-resolve.service';

const typeIdentiteRoute: Routes = [
  {
    path: '',
    component: TypeIdentiteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeIdentiteDetailComponent,
    resolve: {
      typeIdentite: TypeIdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeIdentiteUpdateComponent,
    resolve: {
      typeIdentite: TypeIdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeIdentiteUpdateComponent,
    resolve: {
      typeIdentite: TypeIdentiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeIdentiteRoute)],
  exports: [RouterModule],
})
export class TypeIdentiteRoutingModule {}
