jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISoldeAbsence, SoldeAbsence } from '../solde-absence.model';
import { SoldeAbsenceService } from '../service/solde-absence.service';

import { SoldeAbsenceRoutingResolveService } from './solde-absence-routing-resolve.service';

describe('SoldeAbsence routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SoldeAbsenceRoutingResolveService;
  let service: SoldeAbsenceService;
  let resultSoldeAbsence: ISoldeAbsence | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SoldeAbsenceRoutingResolveService);
    service = TestBed.inject(SoldeAbsenceService);
    resultSoldeAbsence = undefined;
  });

  describe('resolve', () => {
    it('should return ISoldeAbsence returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsence = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsence).toEqual({ id: 123 });
    });

    it('should return new ISoldeAbsence if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsence = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSoldeAbsence).toEqual(new SoldeAbsence());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SoldeAbsence })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsence = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsence).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
