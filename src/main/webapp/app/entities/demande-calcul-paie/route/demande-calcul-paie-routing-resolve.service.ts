import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeCalculPaie, DemandeCalculPaie } from '../demande-calcul-paie.model';
import { DemandeCalculPaieService } from '../service/demande-calcul-paie.service';

@Injectable({ providedIn: 'root' })
export class DemandeCalculPaieRoutingResolveService implements Resolve<IDemandeCalculPaie> {
  constructor(protected service: DemandeCalculPaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeCalculPaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeCalculPaie: HttpResponse<DemandeCalculPaie>) => {
          if (demandeCalculPaie.body) {
            return of(demandeCalculPaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeCalculPaie());
  }
}
