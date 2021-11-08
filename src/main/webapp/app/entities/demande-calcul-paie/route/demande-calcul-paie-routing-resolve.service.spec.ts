jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDemandeCalculPaie, DemandeCalculPaie } from '../demande-calcul-paie.model';
import { DemandeCalculPaieService } from '../service/demande-calcul-paie.service';

import { DemandeCalculPaieRoutingResolveService } from './demande-calcul-paie-routing-resolve.service';

describe('DemandeCalculPaie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DemandeCalculPaieRoutingResolveService;
  let service: DemandeCalculPaieService;
  let resultDemandeCalculPaie: IDemandeCalculPaie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DemandeCalculPaieRoutingResolveService);
    service = TestBed.inject(DemandeCalculPaieService);
    resultDemandeCalculPaie = undefined;
  });

  describe('resolve', () => {
    it('should return IDemandeCalculPaie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeCalculPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeCalculPaie).toEqual({ id: 123 });
    });

    it('should return new IDemandeCalculPaie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeCalculPaie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDemandeCalculPaie).toEqual(new DemandeCalculPaie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandeCalculPaie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeCalculPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeCalculPaie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
