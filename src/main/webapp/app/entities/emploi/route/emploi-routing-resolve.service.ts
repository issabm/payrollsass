import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmploi, Emploi } from '../emploi.model';
import { EmploiService } from '../service/emploi.service';

@Injectable({ providedIn: 'root' })
export class EmploiRoutingResolveService implements Resolve<IEmploi> {
  constructor(protected service: EmploiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmploi> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emploi: HttpResponse<Emploi>) => {
          if (emploi.body) {
            return of(emploi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Emploi());
  }
}
