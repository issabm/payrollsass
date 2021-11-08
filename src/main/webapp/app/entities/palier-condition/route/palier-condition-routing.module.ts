import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PalierConditionComponent } from '../list/palier-condition.component';
import { PalierConditionDetailComponent } from '../detail/palier-condition-detail.component';
import { PalierConditionUpdateComponent } from '../update/palier-condition-update.component';
import { PalierConditionRoutingResolveService } from './palier-condition-routing-resolve.service';

const palierConditionRoute: Routes = [
  {
    path: '',
    component: PalierConditionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PalierConditionDetailComponent,
    resolve: {
      palierCondition: PalierConditionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PalierConditionUpdateComponent,
    resolve: {
      palierCondition: PalierConditionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PalierConditionUpdateComponent,
    resolve: {
      palierCondition: PalierConditionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(palierConditionRoute)],
  exports: [RouterModule],
})
export class PalierConditionRoutingModule {}
