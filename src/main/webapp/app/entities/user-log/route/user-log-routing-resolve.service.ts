import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserLog, UserLog } from '../user-log.model';
import { UserLogService } from '../service/user-log.service';

@Injectable({ providedIn: 'root' })
export class UserLogRoutingResolveService implements Resolve<IUserLog> {
  constructor(protected service: UserLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userLog: HttpResponse<UserLog>) => {
          if (userLog.body) {
            return of(userLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserLog());
  }
}
