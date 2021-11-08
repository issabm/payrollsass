import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITargetEligible, TargetEligible } from '../target-eligible.model';
import { TargetEligibleService } from '../service/target-eligible.service';

@Injectable({ providedIn: 'root' })
export class TargetEligibleRoutingResolveService implements Resolve<ITargetEligible> {
  constructor(protected service: TargetEligibleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITargetEligible> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((targetEligible: HttpResponse<TargetEligible>) => {
          if (targetEligible.body) {
            return of(targetEligible.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TargetEligible());
  }
}
