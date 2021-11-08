import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFrequence, Frequence } from '../frequence.model';
import { FrequenceService } from '../service/frequence.service';

@Injectable({ providedIn: 'root' })
export class FrequenceRoutingResolveService implements Resolve<IFrequence> {
  constructor(protected service: FrequenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFrequence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((frequence: HttpResponse<Frequence>) => {
          if (frequence.body) {
            return of(frequence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Frequence());
  }
}
