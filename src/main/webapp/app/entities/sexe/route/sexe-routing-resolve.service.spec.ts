jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISexe, Sexe } from '../sexe.model';
import { SexeService } from '../service/sexe.service';

import { SexeRoutingResolveService } from './sexe-routing-resolve.service';

describe('Sexe routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SexeRoutingResolveService;
  let service: SexeService;
  let resultSexe: ISexe | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SexeRoutingResolveService);
    service = TestBed.inject(SexeService);
    resultSexe = undefined;
  });

  describe('resolve', () => {
    it('should return ISexe returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSexe).toEqual({ id: 123 });
    });

    it('should return new ISexe if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexe = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSexe).toEqual(new Sexe());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Sexe })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSexe).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
