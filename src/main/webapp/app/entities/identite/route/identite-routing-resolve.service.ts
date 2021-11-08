import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIdentite, Identite } from '../identite.model';
import { IdentiteService } from '../service/identite.service';

@Injectable({ providedIn: 'root' })
export class IdentiteRoutingResolveService implements Resolve<IIdentite> {
  constructor(protected service: IdentiteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdentite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((identite: HttpResponse<Identite>) => {
          if (identite.body) {
            return of(identite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Identite());
  }
}
