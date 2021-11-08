import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INiveauScolaire, NiveauScolaire } from '../niveau-scolaire.model';
import { NiveauScolaireService } from '../service/niveau-scolaire.service';

@Injectable({ providedIn: 'root' })
export class NiveauScolaireRoutingResolveService implements Resolve<INiveauScolaire> {
  constructor(protected service: NiveauScolaireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INiveauScolaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((niveauScolaire: HttpResponse<NiveauScolaire>) => {
          if (niveauScolaire.body) {
            return of(niveauScolaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NiveauScolaire());
  }
}
