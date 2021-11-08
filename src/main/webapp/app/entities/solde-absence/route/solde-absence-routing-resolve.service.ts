import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISoldeAbsence, SoldeAbsence } from '../solde-absence.model';
import { SoldeAbsenceService } from '../service/solde-absence.service';

@Injectable({ providedIn: 'root' })
export class SoldeAbsenceRoutingResolveService implements Resolve<ISoldeAbsence> {
  constructor(protected service: SoldeAbsenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISoldeAbsence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((soldeAbsence: HttpResponse<SoldeAbsence>) => {
          if (soldeAbsence.body) {
            return of(soldeAbsence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SoldeAbsence());
  }
}
