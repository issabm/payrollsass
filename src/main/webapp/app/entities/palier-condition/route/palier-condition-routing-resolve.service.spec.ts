jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPalierCondition, PalierCondition } from '../palier-condition.model';
import { PalierConditionService } from '../service/palier-condition.service';

import { PalierConditionRoutingResolveService } from './palier-condition-routing-resolve.service';

describe('PalierCondition routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PalierConditionRoutingResolveService;
  let service: PalierConditionService;
  let resultPalierCondition: IPalierCondition | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PalierConditionRoutingResolveService);
    service = TestBed.inject(PalierConditionService);
    resultPalierCondition = undefined;
  });

  describe('resolve', () => {
    it('should return IPalierCondition returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierCondition = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierCondition).toEqual({ id: 123 });
    });

    it('should return new IPalierCondition if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierCondition = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPalierCondition).toEqual(new PalierCondition());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PalierCondition })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierCondition = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierCondition).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
