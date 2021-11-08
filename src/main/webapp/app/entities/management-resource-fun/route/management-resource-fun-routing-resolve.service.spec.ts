jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IManagementResourceFun, ManagementResourceFun } from '../management-resource-fun.model';
import { ManagementResourceFunService } from '../service/management-resource-fun.service';

import { ManagementResourceFunRoutingResolveService } from './management-resource-fun-routing-resolve.service';

describe('ManagementResourceFun routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ManagementResourceFunRoutingResolveService;
  let service: ManagementResourceFunService;
  let resultManagementResourceFun: IManagementResourceFun | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ManagementResourceFunRoutingResolveService);
    service = TestBed.inject(ManagementResourceFunService);
    resultManagementResourceFun = undefined;
  });

  describe('resolve', () => {
    it('should return IManagementResourceFun returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceFun = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultManagementResourceFun).toEqual({ id: 123 });
    });

    it('should return new IManagementResourceFun if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceFun = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultManagementResourceFun).toEqual(new ManagementResourceFun());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ManagementResourceFun })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceFun = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultManagementResourceFun).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
