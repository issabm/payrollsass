jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEligibiliteExclude, EligibiliteExclude } from '../eligibilite-exclude.model';
import { EligibiliteExcludeService } from '../service/eligibilite-exclude.service';

import { EligibiliteExcludeRoutingResolveService } from './eligibilite-exclude-routing-resolve.service';

describe('EligibiliteExclude routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EligibiliteExcludeRoutingResolveService;
  let service: EligibiliteExcludeService;
  let resultEligibiliteExclude: IEligibiliteExclude | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EligibiliteExcludeRoutingResolveService);
    service = TestBed.inject(EligibiliteExcludeService);
    resultEligibiliteExclude = undefined;
  });

  describe('resolve', () => {
    it('should return IEligibiliteExclude returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibiliteExclude = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEligibiliteExclude).toEqual({ id: 123 });
    });

    it('should return new IEligibiliteExclude if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibiliteExclude = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEligibiliteExclude).toEqual(new EligibiliteExclude());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EligibiliteExclude })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibiliteExclude = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEligibiliteExclude).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
