jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEligibilite, Eligibilite } from '../eligibilite.model';
import { EligibiliteService } from '../service/eligibilite.service';

import { EligibiliteRoutingResolveService } from './eligibilite-routing-resolve.service';

describe('Eligibilite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EligibiliteRoutingResolveService;
  let service: EligibiliteService;
  let resultEligibilite: IEligibilite | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EligibiliteRoutingResolveService);
    service = TestBed.inject(EligibiliteService);
    resultEligibilite = undefined;
  });

  describe('resolve', () => {
    it('should return IEligibilite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibilite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEligibilite).toEqual({ id: 123 });
    });

    it('should return new IEligibilite if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibilite = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEligibilite).toEqual(new Eligibilite());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Eligibilite })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEligibilite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEligibilite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
