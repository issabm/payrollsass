jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICarriere, Carriere } from '../carriere.model';
import { CarriereService } from '../service/carriere.service';

import { CarriereRoutingResolveService } from './carriere-routing-resolve.service';

describe('Carriere routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CarriereRoutingResolveService;
  let service: CarriereService;
  let resultCarriere: ICarriere | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(CarriereRoutingResolveService);
    service = TestBed.inject(CarriereService);
    resultCarriere = undefined;
  });

  describe('resolve', () => {
    it('should return ICarriere returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCarriere = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCarriere).toEqual({ id: 123 });
    });

    it('should return new ICarriere if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCarriere = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCarriere).toEqual(new Carriere());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Carriere })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCarriere = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCarriere).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
