import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEchlon, Echlon } from '../echlon.model';
import { EchlonService } from '../service/echlon.service';

@Injectable({ providedIn: 'root' })
export class EchlonRoutingResolveService implements Resolve<IEchlon> {
  constructor(protected service: EchlonService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEchlon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((echlon: HttpResponse<Echlon>) => {
          if (echlon.body) {
            return of(echlon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Echlon());
  }
}
