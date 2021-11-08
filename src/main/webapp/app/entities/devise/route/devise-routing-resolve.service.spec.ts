jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDevise, Devise } from '../devise.model';
import { DeviseService } from '../service/devise.service';

import { DeviseRoutingResolveService } from './devise-routing-resolve.service';

describe('Devise routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DeviseRoutingResolveService;
  let service: DeviseService;
  let resultDevise: IDevise | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DeviseRoutingResolveService);
    service = TestBed.inject(DeviseService);
    resultDevise = undefined;
  });

  describe('resolve', () => {
    it('should return IDevise returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDevise = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDevise).toEqual({ id: 123 });
    });

    it('should return new IDevise if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDevise = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDevise).toEqual(new Devise());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Devise })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDevise = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDevise).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
