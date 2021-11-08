jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureAdhesion, NatureAdhesion } from '../nature-adhesion.model';
import { NatureAdhesionService } from '../service/nature-adhesion.service';

import { NatureAdhesionRoutingResolveService } from './nature-adhesion-routing-resolve.service';

describe('NatureAdhesion routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NatureAdhesionRoutingResolveService;
  let service: NatureAdhesionService;
  let resultNatureAdhesion: INatureAdhesion | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NatureAdhesionRoutingResolveService);
    service = TestBed.inject(NatureAdhesionService);
    resultNatureAdhesion = undefined;
  });

  describe('resolve', () => {
    it('should return INatureAdhesion returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAdhesion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureAdhesion).toEqual({ id: 123 });
    });

    it('should return new INatureAdhesion if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAdhesion = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNatureAdhesion).toEqual(new NatureAdhesion());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureAdhesion })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAdhesion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureAdhesion).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
