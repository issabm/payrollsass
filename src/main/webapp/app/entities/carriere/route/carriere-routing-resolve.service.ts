import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarriere, Carriere } from '../carriere.model';
import { CarriereService } from '../service/carriere.service';

@Injectable({ providedIn: 'root' })
export class CarriereRoutingResolveService implements Resolve<ICarriere> {
  constructor(protected service: CarriereService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarriere> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((carriere: HttpResponse<Carriere>) => {
          if (carriere.body) {
            return of(carriere.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Carriere());
  }
}
