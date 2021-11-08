import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureEligibilite, NatureEligibilite } from '../nature-eligibilite.model';
import { NatureEligibiliteService } from '../service/nature-eligibilite.service';

@Injectable({ providedIn: 'root' })
export class NatureEligibiliteRoutingResolveService implements Resolve<INatureEligibilite> {
  constructor(protected service: NatureEligibiliteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureEligibilite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureEligibilite: HttpResponse<NatureEligibilite>) => {
          if (natureEligibilite.body) {
            return of(natureEligibilite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureEligibilite());
  }
}
