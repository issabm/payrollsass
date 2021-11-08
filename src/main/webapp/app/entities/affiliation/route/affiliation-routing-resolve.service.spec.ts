jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAffiliation, Affiliation } from '../affiliation.model';
import { AffiliationService } from '../service/affiliation.service';

import { AffiliationRoutingResolveService } from './affiliation-routing-resolve.service';

describe('Affiliation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AffiliationRoutingResolveService;
  let service: AffiliationService;
  let resultAffiliation: IAffiliation | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(AffiliationRoutingResolveService);
    service = TestBed.inject(AffiliationService);
    resultAffiliation = undefined;
  });

  describe('resolve', () => {
    it('should return IAffiliation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAffiliation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAffiliation).toEqual({ id: 123 });
    });

    it('should return new IAffiliation if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAffiliation = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAffiliation).toEqual(new Affiliation());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Affiliation })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAffiliation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAffiliation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
