import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntreprise, Entreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';

@Injectable({ providedIn: 'root' })
export class EntrepriseRoutingResolveService implements Resolve<IEntreprise> {
  constructor(protected service: EntrepriseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntreprise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((entreprise: HttpResponse<Entreprise>) => {
          if (entreprise.body) {
            return of(entreprise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entreprise());
  }
}
