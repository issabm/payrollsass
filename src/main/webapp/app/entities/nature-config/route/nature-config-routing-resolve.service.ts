import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureConfig, NatureConfig } from '../nature-config.model';
import { NatureConfigService } from '../service/nature-config.service';

@Injectable({ providedIn: 'root' })
export class NatureConfigRoutingResolveService implements Resolve<INatureConfig> {
  constructor(protected service: NatureConfigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureConfig: HttpResponse<NatureConfig>) => {
          if (natureConfig.body) {
            return of(natureConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureConfig());
  }
}
