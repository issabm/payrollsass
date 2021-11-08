jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmploi, Emploi } from '../emploi.model';
import { EmploiService } from '../service/emploi.service';

import { EmploiRoutingResolveService } from './emploi-routing-resolve.service';

describe('Emploi routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EmploiRoutingResolveService;
  let service: EmploiService;
  let resultEmploi: IEmploi | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EmploiRoutingResolveService);
    service = TestBed.inject(EmploiService);
    resultEmploi = undefined;
  });

  describe('resolve', () => {
    it('should return IEmploi returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmploi = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmploi).toEqual({ id: 123 });
    });

    it('should return new IEmploi if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmploi = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmploi).toEqual(new Emploi());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Emploi })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmploi = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmploi).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
