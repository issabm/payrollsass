import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConjoint, Conjoint } from '../conjoint.model';
import { ConjointService } from '../service/conjoint.service';

@Injectable({ providedIn: 'root' })
export class ConjointRoutingResolveService implements Resolve<IConjoint> {
  constructor(protected service: ConjointService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConjoint> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((conjoint: HttpResponse<Conjoint>) => {
          if (conjoint.body) {
            return of(conjoint.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Conjoint());
  }
}
