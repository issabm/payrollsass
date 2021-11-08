import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModeInput, ModeInput } from '../mode-input.model';
import { ModeInputService } from '../service/mode-input.service';

@Injectable({ providedIn: 'root' })
export class ModeInputRoutingResolveService implements Resolve<IModeInput> {
  constructor(protected service: ModeInputService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeInput> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((modeInput: HttpResponse<ModeInput>) => {
          if (modeInput.body) {
            return of(modeInput.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModeInput());
  }
}
