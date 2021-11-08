import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlateInfo, PlateInfo } from '../plate-info.model';
import { PlateInfoService } from '../service/plate-info.service';

@Injectable({ providedIn: 'root' })
export class PlateInfoRoutingResolveService implements Resolve<IPlateInfo> {
  constructor(protected service: PlateInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlateInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((plateInfo: HttpResponse<PlateInfo>) => {
          if (plateInfo.body) {
            return of(plateInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlateInfo());
  }
}
