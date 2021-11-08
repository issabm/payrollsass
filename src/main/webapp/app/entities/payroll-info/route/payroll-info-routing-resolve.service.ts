import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPayrollInfo, PayrollInfo } from '../payroll-info.model';
import { PayrollInfoService } from '../service/payroll-info.service';

@Injectable({ providedIn: 'root' })
export class PayrollInfoRoutingResolveService implements Resolve<IPayrollInfo> {
  constructor(protected service: PayrollInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayrollInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((payrollInfo: HttpResponse<PayrollInfo>) => {
          if (payrollInfo.body) {
            return of(payrollInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayrollInfo());
  }
}
