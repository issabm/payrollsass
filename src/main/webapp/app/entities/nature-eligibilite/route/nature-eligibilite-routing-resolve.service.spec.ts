jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureEligibilite, NatureEligibilite } from '../nature-eligibilite.model';
import { NatureEligibiliteService } from '../service/nature-eligibilite.service';

import { NatureEligibiliteRoutingResolveService } from './nature-eligibilite-routing-resolve.service';

describe('NatureEligibilite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NatureEligibiliteRoutingResolveService;
  let service: NatureEligibiliteService;
  let resultNatureEligibilite: INatureEligibilite | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NatureEligibiliteRoutingResolveService);
    service = TestBed.inject(NatureEligibiliteService);
    resultNatureEligibilite = undefined;
  });

  describe('resolve', () => {
    it('should return INatureEligibilite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureEligibilite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureEligibilite).toEqual({ id: 123 });
    });

    it('should return new INatureEligibilite if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureEligibilite = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNatureEligibilite).toEqual(new NatureEligibilite());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureEligibilite })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureEligibilite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureEligibilite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
