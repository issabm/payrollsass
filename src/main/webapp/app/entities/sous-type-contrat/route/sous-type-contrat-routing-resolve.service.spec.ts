jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISousTypeContrat, SousTypeContrat } from '../sous-type-contrat.model';
import { SousTypeContratService } from '../service/sous-type-contrat.service';

import { SousTypeContratRoutingResolveService } from './sous-type-contrat-routing-resolve.service';

describe('SousTypeContrat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SousTypeContratRoutingResolveService;
  let service: SousTypeContratService;
  let resultSousTypeContrat: ISousTypeContrat | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SousTypeContratRoutingResolveService);
    service = TestBed.inject(SousTypeContratService);
    resultSousTypeContrat = undefined;
  });

  describe('resolve', () => {
    it('should return ISousTypeContrat returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSousTypeContrat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSousTypeContrat).toEqual({ id: 123 });
    });

    it('should return new ISousTypeContrat if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSousTypeContrat = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSousTypeContrat).toEqual(new SousTypeContrat());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SousTypeContrat })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSousTypeContrat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSousTypeContrat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
