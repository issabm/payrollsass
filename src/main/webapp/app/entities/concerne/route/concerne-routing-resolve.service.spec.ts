jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IConcerne, Concerne } from '../concerne.model';
import { ConcerneService } from '../service/concerne.service';

import { ConcerneRoutingResolveService } from './concerne-routing-resolve.service';

describe('Concerne routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ConcerneRoutingResolveService;
  let service: ConcerneService;
  let resultConcerne: IConcerne | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ConcerneRoutingResolveService);
    service = TestBed.inject(ConcerneService);
    resultConcerne = undefined;
  });

  describe('resolve', () => {
    it('should return IConcerne returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConcerne = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConcerne).toEqual({ id: 123 });
    });

    it('should return new IConcerne if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConcerne = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultConcerne).toEqual(new Concerne());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Concerne })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConcerne = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConcerne).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
