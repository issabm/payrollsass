import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureMvtPaie, NatureMvtPaie } from '../nature-mvt-paie.model';
import { NatureMvtPaieService } from '../service/nature-mvt-paie.service';

@Injectable({ providedIn: 'root' })
export class NatureMvtPaieRoutingResolveService implements Resolve<INatureMvtPaie> {
  constructor(protected service: NatureMvtPaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureMvtPaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureMvtPaie: HttpResponse<NatureMvtPaie>) => {
          if (natureMvtPaie.body) {
            return of(natureMvtPaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureMvtPaie());
  }
}
