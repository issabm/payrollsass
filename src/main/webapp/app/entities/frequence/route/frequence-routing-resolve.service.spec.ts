jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFrequence, Frequence } from '../frequence.model';
import { FrequenceService } from '../service/frequence.service';

import { FrequenceRoutingResolveService } from './frequence-routing-resolve.service';

describe('Frequence routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FrequenceRoutingResolveService;
  let service: FrequenceService;
  let resultFrequence: IFrequence | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FrequenceRoutingResolveService);
    service = TestBed.inject(FrequenceService);
    resultFrequence = undefined;
  });

  describe('resolve', () => {
    it('should return IFrequence returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFrequence = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFrequence).toEqual({ id: 123 });
    });

    it('should return new IFrequence if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFrequence = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFrequence).toEqual(new Frequence());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Frequence })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFrequence = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFrequence).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
