jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISoldeAbsencePaie, SoldeAbsencePaie } from '../solde-absence-paie.model';
import { SoldeAbsencePaieService } from '../service/solde-absence-paie.service';

import { SoldeAbsencePaieRoutingResolveService } from './solde-absence-paie-routing-resolve.service';

describe('SoldeAbsencePaie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SoldeAbsencePaieRoutingResolveService;
  let service: SoldeAbsencePaieService;
  let resultSoldeAbsencePaie: ISoldeAbsencePaie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SoldeAbsencePaieRoutingResolveService);
    service = TestBed.inject(SoldeAbsencePaieService);
    resultSoldeAbsencePaie = undefined;
  });

  describe('resolve', () => {
    it('should return ISoldeAbsencePaie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsencePaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsencePaie).toEqual({ id: 123 });
    });

    it('should return new ISoldeAbsencePaie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsencePaie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSoldeAbsencePaie).toEqual(new SoldeAbsencePaie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SoldeAbsencePaie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsencePaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsencePaie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
