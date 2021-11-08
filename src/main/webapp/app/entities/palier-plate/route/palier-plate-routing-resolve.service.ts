import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPalierPlate, PalierPlate } from '../palier-plate.model';
import { PalierPlateService } from '../service/palier-plate.service';

@Injectable({ providedIn: 'root' })
export class PalierPlateRoutingResolveService implements Resolve<IPalierPlate> {
  constructor(protected service: PalierPlateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPalierPlate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((palierPlate: HttpResponse<PalierPlate>) => {
          if (palierPlate.body) {
            return of(palierPlate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PalierPlate());
  }
}
