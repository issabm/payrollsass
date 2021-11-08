import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';
import { MouvementPaieService } from '../service/mouvement-paie.service';

@Injectable({ providedIn: 'root' })
export class MouvementPaieRoutingResolveService implements Resolve<IMouvementPaie> {
  constructor(protected service: MouvementPaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMouvementPaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mouvementPaie: HttpResponse<MouvementPaie>) => {
          if (mouvementPaie.body) {
            return of(mouvementPaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MouvementPaie());
  }
}
