jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureConfig, NatureConfig } from '../nature-config.model';
import { NatureConfigService } from '../service/nature-config.service';

import { NatureConfigRoutingResolveService } from './nature-config-routing-resolve.service';

describe('NatureConfig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NatureConfigRoutingResolveService;
  let service: NatureConfigService;
  let resultNatureConfig: INatureConfig | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NatureConfigRoutingResolveService);
    service = TestBed.inject(NatureConfigService);
    resultNatureConfig = undefined;
  });

  describe('resolve', () => {
    it('should return INatureConfig returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureConfig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureConfig).toEqual({ id: 123 });
    });

    it('should return new INatureConfig if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureConfig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNatureConfig).toEqual(new NatureConfig());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureConfig })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureConfig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureConfig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
