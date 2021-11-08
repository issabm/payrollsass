jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRebrique, Rebrique } from '../rebrique.model';
import { RebriqueService } from '../service/rebrique.service';

import { RebriqueRoutingResolveService } from './rebrique-routing-resolve.service';

describe('Rebrique routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RebriqueRoutingResolveService;
  let service: RebriqueService;
  let resultRebrique: IRebrique | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(RebriqueRoutingResolveService);
    service = TestBed.inject(RebriqueService);
    resultRebrique = undefined;
  });

  describe('resolve', () => {
    it('should return IRebrique returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRebrique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRebrique).toEqual({ id: 123 });
    });

    it('should return new IRebrique if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRebrique = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRebrique).toEqual(new Rebrique());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Rebrique })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRebrique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRebrique).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
