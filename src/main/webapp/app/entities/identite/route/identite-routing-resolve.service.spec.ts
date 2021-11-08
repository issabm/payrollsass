jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IIdentite, Identite } from '../identite.model';
import { IdentiteService } from '../service/identite.service';

import { IdentiteRoutingResolveService } from './identite-routing-resolve.service';

describe('Identite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: IdentiteRoutingResolveService;
  let service: IdentiteService;
  let resultIdentite: IIdentite | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(IdentiteRoutingResolveService);
    service = TestBed.inject(IdentiteService);
    resultIdentite = undefined;
  });

  describe('resolve', () => {
    it('should return IIdentite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIdentite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIdentite).toEqual({ id: 123 });
    });

    it('should return new IIdentite if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIdentite = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIdentite).toEqual(new Identite());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Identite })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIdentite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIdentite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
