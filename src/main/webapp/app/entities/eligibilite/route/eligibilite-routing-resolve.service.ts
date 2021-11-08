import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEligibilite, Eligibilite } from '../eligibilite.model';
import { EligibiliteService } from '../service/eligibilite.service';

@Injectable({ providedIn: 'root' })
export class EligibiliteRoutingResolveService implements Resolve<IEligibilite> {
  constructor(protected service: EligibiliteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEligibilite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eligibilite: HttpResponse<Eligibilite>) => {
          if (eligibilite.body) {
            return of(eligibilite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Eligibilite());
  }
}
