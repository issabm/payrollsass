import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEligibiliteExclude, EligibiliteExclude } from '../eligibilite-exclude.model';
import { EligibiliteExcludeService } from '../service/eligibilite-exclude.service';

@Injectable({ providedIn: 'root' })
export class EligibiliteExcludeRoutingResolveService implements Resolve<IEligibiliteExclude> {
  constructor(protected service: EligibiliteExcludeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEligibiliteExclude> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eligibiliteExclude: HttpResponse<EligibiliteExclude>) => {
          if (eligibiliteExclude.body) {
            return of(eligibiliteExclude.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EligibiliteExclude());
  }
}
