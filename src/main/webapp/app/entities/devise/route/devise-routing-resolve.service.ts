import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDevise, Devise } from '../devise.model';
import { DeviseService } from '../service/devise.service';

@Injectable({ providedIn: 'root' })
export class DeviseRoutingResolveService implements Resolve<IDevise> {
  constructor(protected service: DeviseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((devise: HttpResponse<Devise>) => {
          if (devise.body) {
            return of(devise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Devise());
  }
}
