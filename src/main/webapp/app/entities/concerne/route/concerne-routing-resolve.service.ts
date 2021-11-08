import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConcerne, Concerne } from '../concerne.model';
import { ConcerneService } from '../service/concerne.service';

@Injectable({ providedIn: 'root' })
export class ConcerneRoutingResolveService implements Resolve<IConcerne> {
  constructor(protected service: ConcerneService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConcerne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((concerne: HttpResponse<Concerne>) => {
          if (concerne.body) {
            return of(concerne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Concerne());
  }
}
