import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeHandicapComponent } from '../list/type-handicap.component';
import { TypeHandicapDetailComponent } from '../detail/type-handicap-detail.component';
import { TypeHandicapUpdateComponent } from '../update/type-handicap-update.component';
import { TypeHandicapRoutingResolveService } from './type-handicap-routing-resolve.service';

const typeHandicapRoute: Routes = [
  {
    path: '',
    component: TypeHandicapComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeHandicapDetailComponent,
    resolve: {
      typeHandicap: TypeHandicapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeHandicapUpdateComponent,
    resolve: {
      typeHandicap: TypeHandicapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeHandicapUpdateComponent,
    resolve: {
      typeHandicap: TypeHandicapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeHandicapRoute)],
  exports: [RouterModule],
})
export class TypeHandicapRoutingModule {}
