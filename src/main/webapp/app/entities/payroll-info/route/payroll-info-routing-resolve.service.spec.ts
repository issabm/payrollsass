jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPayrollInfo, PayrollInfo } from '../payroll-info.model';
import { PayrollInfoService } from '../service/payroll-info.service';

import { PayrollInfoRoutingResolveService } from './payroll-info-routing-resolve.service';

describe('PayrollInfo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PayrollInfoRoutingResolveService;
  let service: PayrollInfoService;
  let resultPayrollInfo: IPayrollInfo | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PayrollInfoRoutingResolveService);
    service = TestBed.inject(PayrollInfoService);
    resultPayrollInfo = undefined;
  });

  describe('resolve', () => {
    it('should return IPayrollInfo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayrollInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPayrollInfo).toEqual({ id: 123 });
    });

    it('should return new IPayrollInfo if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayrollInfo = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPayrollInfo).toEqual(new PayrollInfo());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PayrollInfo })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayrollInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPayrollInfo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
