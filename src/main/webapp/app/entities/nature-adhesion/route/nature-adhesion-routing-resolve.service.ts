import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureAdhesion, NatureAdhesion } from '../nature-adhesion.model';
import { NatureAdhesionService } from '../service/nature-adhesion.service';

@Injectable({ providedIn: 'root' })
export class NatureAdhesionRoutingResolveService implements Resolve<INatureAdhesion> {
  constructor(protected service: NatureAdhesionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureAdhesion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureAdhesion: HttpResponse<NatureAdhesion>) => {
          if (natureAdhesion.body) {
            return of(natureAdhesion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureAdhesion());
  }
}
