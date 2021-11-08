jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITargetEligible, TargetEligible } from '../target-eligible.model';
import { TargetEligibleService } from '../service/target-eligible.service';

import { TargetEligibleRoutingResolveService } from './target-eligible-routing-resolve.service';

describe('TargetEligible routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TargetEligibleRoutingResolveService;
  let service: TargetEligibleService;
  let resultTargetEligible: ITargetEligible | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TargetEligibleRoutingResolveService);
    service = TestBed.inject(TargetEligibleService);
    resultTargetEligible = undefined;
  });

  describe('resolve', () => {
    it('should return ITargetEligible returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTargetEligible = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTargetEligible).toEqual({ id: 123 });
    });

    it('should return new ITargetEligible if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTargetEligible = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTargetEligible).toEqual(new TargetEligible());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TargetEligible })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTargetEligible = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTargetEligible).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
