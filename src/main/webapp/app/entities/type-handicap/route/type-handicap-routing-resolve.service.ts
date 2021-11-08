import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeHandicap, TypeHandicap } from '../type-handicap.model';
import { TypeHandicapService } from '../service/type-handicap.service';

@Injectable({ providedIn: 'root' })
export class TypeHandicapRoutingResolveService implements Resolve<ITypeHandicap> {
  constructor(protected service: TypeHandicapService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeHandicap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeHandicap: HttpResponse<TypeHandicap>) => {
          if (typeHandicap.body) {
            return of(typeHandicap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeHandicap());
  }
}
