import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPalierCondition, PalierCondition } from '../palier-condition.model';
import { PalierConditionService } from '../service/palier-condition.service';

@Injectable({ providedIn: 'root' })
export class PalierConditionRoutingResolveService implements Resolve<IPalierCondition> {
  constructor(protected service: PalierConditionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPalierCondition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((palierCondition: HttpResponse<PalierCondition>) => {
          if (palierCondition.body) {
            return of(palierCondition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PalierCondition());
  }
}
