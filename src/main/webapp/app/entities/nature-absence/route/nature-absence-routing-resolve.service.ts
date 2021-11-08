import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureAbsence, NatureAbsence } from '../nature-absence.model';
import { NatureAbsenceService } from '../service/nature-absence.service';

@Injectable({ providedIn: 'root' })
export class NatureAbsenceRoutingResolveService implements Resolve<INatureAbsence> {
  constructor(protected service: NatureAbsenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureAbsence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureAbsence: HttpResponse<NatureAbsence>) => {
          if (natureAbsence.body) {
            return of(natureAbsence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureAbsence());
  }
}
