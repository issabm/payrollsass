jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IConjoint, Conjoint } from '../conjoint.model';
import { ConjointService } from '../service/conjoint.service';

import { ConjointRoutingResolveService } from './conjoint-routing-resolve.service';

describe('Conjoint routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ConjointRoutingResolveService;
  let service: ConjointService;
  let resultConjoint: IConjoint | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ConjointRoutingResolveService);
    service = TestBed.inject(ConjointService);
    resultConjoint = undefined;
  });

  describe('resolve', () => {
    it('should return IConjoint returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConjoint = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConjoint).toEqual({ id: 123 });
    });

    it('should return new IConjoint if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConjoint = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultConjoint).toEqual(new Conjoint());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Conjoint })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConjoint = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConjoint).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
