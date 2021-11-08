import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAffiliation, Affiliation } from '../affiliation.model';
import { AffiliationService } from '../service/affiliation.service';

@Injectable({ providedIn: 'root' })
export class AffiliationRoutingResolveService implements Resolve<IAffiliation> {
  constructor(protected service: AffiliationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAffiliation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((affiliation: HttpResponse<Affiliation>) => {
          if (affiliation.body) {
            return of(affiliation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Affiliation());
  }
}
