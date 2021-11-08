import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeIdentite, TypeIdentite } from '../type-identite.model';
import { TypeIdentiteService } from '../service/type-identite.service';

@Injectable({ providedIn: 'root' })
export class TypeIdentiteRoutingResolveService implements Resolve<ITypeIdentite> {
  constructor(protected service: TypeIdentiteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeIdentite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeIdentite: HttpResponse<TypeIdentite>) => {
          if (typeIdentite.body) {
            return of(typeIdentite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeIdentite());
  }
}
