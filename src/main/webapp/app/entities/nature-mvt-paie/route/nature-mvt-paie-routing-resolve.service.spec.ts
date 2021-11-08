jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureMvtPaie, NatureMvtPaie } from '../nature-mvt-paie.model';
import { NatureMvtPaieService } from '../service/nature-mvt-paie.service';

import { NatureMvtPaieRoutingResolveService } from './nature-mvt-paie-routing-resolve.service';

describe('NatureMvtPaie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NatureMvtPaieRoutingResolveService;
  let service: NatureMvtPaieService;
  let resultNatureMvtPaie: INatureMvtPaie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NatureMvtPaieRoutingResolveService);
    service = TestBed.inject(NatureMvtPaieService);
    resultNatureMvtPaie = undefined;
  });

  describe('resolve', () => {
    it('should return INatureMvtPaie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureMvtPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureMvtPaie).toEqual({ id: 123 });
    });

    it('should return new INatureMvtPaie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureMvtPaie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNatureMvtPaie).toEqual(new NatureMvtPaie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureMvtPaie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureMvtPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureMvtPaie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
