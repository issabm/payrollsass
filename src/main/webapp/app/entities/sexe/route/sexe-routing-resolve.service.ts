import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISexe, Sexe } from '../sexe.model';
import { SexeService } from '../service/sexe.service';

@Injectable({ providedIn: 'root' })
export class SexeRoutingResolveService implements Resolve<ISexe> {
  constructor(protected service: SexeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISexe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sexe: HttpResponse<Sexe>) => {
          if (sexe.body) {
            return of(sexe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sexe());
  }
}
