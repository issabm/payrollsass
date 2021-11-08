import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntityAdhesion, EntityAdhesion } from '../entity-adhesion.model';
import { EntityAdhesionService } from '../service/entity-adhesion.service';

@Injectable({ providedIn: 'root' })
export class EntityAdhesionRoutingResolveService implements Resolve<IEntityAdhesion> {
  constructor(protected service: EntityAdhesionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityAdhesion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((entityAdhesion: HttpResponse<EntityAdhesion>) => {
          if (entityAdhesion.body) {
            return of(entityAdhesion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityAdhesion());
  }
}
