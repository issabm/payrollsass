jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPalierPlate, PalierPlate } from '../palier-plate.model';
import { PalierPlateService } from '../service/palier-plate.service';

import { PalierPlateRoutingResolveService } from './palier-plate-routing-resolve.service';

describe('PalierPlate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PalierPlateRoutingResolveService;
  let service: PalierPlateService;
  let resultPalierPlate: IPalierPlate | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PalierPlateRoutingResolveService);
    service = TestBed.inject(PalierPlateService);
    resultPalierPlate = undefined;
  });

  describe('resolve', () => {
    it('should return IPalierPlate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierPlate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierPlate).toEqual({ id: 123 });
    });

    it('should return new IPalierPlate if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierPlate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPalierPlate).toEqual(new PalierPlate());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PalierPlate })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierPlate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierPlate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
