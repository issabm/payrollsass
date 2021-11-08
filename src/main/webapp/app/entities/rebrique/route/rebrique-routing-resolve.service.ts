import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRebrique, Rebrique } from '../rebrique.model';
import { RebriqueService } from '../service/rebrique.service';

@Injectable({ providedIn: 'root' })
export class RebriqueRoutingResolveService implements Resolve<IRebrique> {
  constructor(protected service: RebriqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRebrique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rebrique: HttpResponse<Rebrique>) => {
          if (rebrique.body) {
            return of(rebrique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rebrique());
  }
}
