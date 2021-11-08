import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISens, Sens } from '../sens.model';
import { SensService } from '../service/sens.service';

@Injectable({ providedIn: 'root' })
export class SensRoutingResolveService implements Resolve<ISens> {
  constructor(protected service: SensService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISens> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sens: HttpResponse<Sens>) => {
          if (sens.body) {
            return of(sens.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sens());
  }
}
