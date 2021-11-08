import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISousTypeContrat, SousTypeContrat } from '../sous-type-contrat.model';
import { SousTypeContratService } from '../service/sous-type-contrat.service';

@Injectable({ providedIn: 'root' })
export class SousTypeContratRoutingResolveService implements Resolve<ISousTypeContrat> {
  constructor(protected service: SousTypeContratService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISousTypeContrat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sousTypeContrat: HttpResponse<SousTypeContrat>) => {
          if (sousTypeContrat.body) {
            return of(sousTypeContrat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SousTypeContrat());
  }
}
