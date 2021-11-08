jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITypeHandicap, TypeHandicap } from '../type-handicap.model';
import { TypeHandicapService } from '../service/type-handicap.service';

import { TypeHandicapRoutingResolveService } from './type-handicap-routing-resolve.service';

describe('TypeHandicap routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TypeHandicapRoutingResolveService;
  let service: TypeHandicapService;
  let resultTypeHandicap: ITypeHandicap | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TypeHandicapRoutingResolveService);
    service = TestBed.inject(TypeHandicapService);
    resultTypeHandicap = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeHandicap returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeHandicap = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeHandicap).toEqual({ id: 123 });
    });

    it('should return new ITypeHandicap if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeHandicap = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeHandicap).toEqual(new TypeHandicap());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TypeHandicap })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeHandicap = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeHandicap).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
