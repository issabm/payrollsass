jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEntityAdhesion, EntityAdhesion } from '../entity-adhesion.model';
import { EntityAdhesionService } from '../service/entity-adhesion.service';

import { EntityAdhesionRoutingResolveService } from './entity-adhesion-routing-resolve.service';

describe('EntityAdhesion routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EntityAdhesionRoutingResolveService;
  let service: EntityAdhesionService;
  let resultEntityAdhesion: IEntityAdhesion | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EntityAdhesionRoutingResolveService);
    service = TestBed.inject(EntityAdhesionService);
    resultEntityAdhesion = undefined;
  });

  describe('resolve', () => {
    it('should return IEntityAdhesion returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEntityAdhesion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEntityAdhesion).toEqual({ id: 123 });
    });

    it('should return new IEntityAdhesion if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEntityAdhesion = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEntityAdhesion).toEqual(new EntityAdhesion());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EntityAdhesion })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEntityAdhesion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEntityAdhesion).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
