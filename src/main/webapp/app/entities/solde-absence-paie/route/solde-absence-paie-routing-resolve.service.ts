import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISoldeAbsencePaie, SoldeAbsencePaie } from '../solde-absence-paie.model';
import { SoldeAbsencePaieService } from '../service/solde-absence-paie.service';

@Injectable({ providedIn: 'root' })
export class SoldeAbsencePaieRoutingResolveService implements Resolve<ISoldeAbsencePaie> {
  constructor(protected service: SoldeAbsencePaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISoldeAbsencePaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((soldeAbsencePaie: HttpResponse<SoldeAbsencePaie>) => {
          if (soldeAbsencePaie.body) {
            return of(soldeAbsencePaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SoldeAbsencePaie());
  }
}
