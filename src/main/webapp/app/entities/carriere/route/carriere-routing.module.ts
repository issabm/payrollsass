import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CarriereComponent } from '../list/carriere.component';
import { CarriereDetailComponent } from '../detail/carriere-detail.component';
import { CarriereUpdateComponent } from '../update/carriere-update.component';
import { CarriereRoutingResolveService } from './carriere-routing-resolve.service';

const carriereRoute: Routes = [
  {
    path: '',
    component: CarriereComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarriereDetailComponent,
    resolve: {
      carriere: CarriereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarriereUpdateComponent,
    resolve: {
      carriere: CarriereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarriereUpdateComponent,
    resolve: {
      carriere: CarriereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(carriereRoute)],
  exports: [RouterModule],
})
export class CarriereRoutingModule {}
