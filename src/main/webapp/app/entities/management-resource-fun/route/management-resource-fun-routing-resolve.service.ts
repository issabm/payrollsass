import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManagementResourceFun, ManagementResourceFun } from '../management-resource-fun.model';
import { ManagementResourceFunService } from '../service/management-resource-fun.service';

@Injectable({ providedIn: 'root' })
export class ManagementResourceFunRoutingResolveService implements Resolve<IManagementResourceFun> {
  constructor(protected service: ManagementResourceFunService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IManagementResourceFun> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((managementResourceFun: HttpResponse<ManagementResourceFun>) => {
          if (managementResourceFun.body) {
            return of(managementResourceFun.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ManagementResourceFun());
  }
}
