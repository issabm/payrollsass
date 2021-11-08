jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITypeIdentite, TypeIdentite } from '../type-identite.model';
import { TypeIdentiteService } from '../service/type-identite.service';

import { TypeIdentiteRoutingResolveService } from './type-identite-routing-resolve.service';

describe('TypeIdentite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TypeIdentiteRoutingResolveService;
  let service: TypeIdentiteService;
  let resultTypeIdentite: ITypeIdentite | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TypeIdentiteRoutingResolveService);
    service = TestBed.inject(TypeIdentiteService);
    resultTypeIdentite = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeIdentite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeIdentite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeIdentite).toEqual({ id: 123 });
    });

    it('should return new ITypeIdentite if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeIdentite = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeIdentite).toEqual(new TypeIdentite());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TypeIdentite })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeIdentite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeIdentite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
