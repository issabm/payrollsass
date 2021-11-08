jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEchlon, Echlon } from '../echlon.model';
import { EchlonService } from '../service/echlon.service';

import { EchlonRoutingResolveService } from './echlon-routing-resolve.service';

describe('Echlon routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EchlonRoutingResolveService;
  let service: EchlonService;
  let resultEchlon: IEchlon | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EchlonRoutingResolveService);
    service = TestBed.inject(EchlonService);
    resultEchlon = undefined;
  });

  describe('resolve', () => {
    it('should return IEchlon returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEchlon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEchlon).toEqual({ id: 123 });
    });

    it('should return new IEchlon if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEchlon = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEchlon).toEqual(new Echlon());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Echlon })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEchlon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEchlon).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
