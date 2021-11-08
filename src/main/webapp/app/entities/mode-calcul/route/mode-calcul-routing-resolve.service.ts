import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModeCalcul, ModeCalcul } from '../mode-calcul.model';
import { ModeCalculService } from '../service/mode-calcul.service';

@Injectable({ providedIn: 'root' })
export class ModeCalculRoutingResolveService implements Resolve<IModeCalcul> {
  constructor(protected service: ModeCalculService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeCalcul> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((modeCalcul: HttpResponse<ModeCalcul>) => {
          if (modeCalcul.body) {
            return of(modeCalcul.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModeCalcul());
  }
}
