import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManagementResource, ManagementResource } from '../management-resource.model';
import { ManagementResourceService } from '../service/management-resource.service';

@Injectable({ providedIn: 'root' })
export class ManagementResourceRoutingResolveService implements Resolve<IManagementResource> {
  constructor(protected service: ManagementResourceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IManagementResource> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((managementResource: HttpResponse<ManagementResource>) => {
          if (managementResource.body) {
            return of(managementResource.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ManagementResource());
  }
}
