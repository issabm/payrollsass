import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IManagementResource, getManagementResourceIdentifier } from '../management-resource.model';

export type EntityResponseType = HttpResponse<IManagementResource>;
export type EntityArrayResponseType = HttpResponse<IManagementResource[]>;

@Injectable({ providedIn: 'root' })
export class ManagementResourceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/management-resources');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(managementResource: IManagementResource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResource);
    return this.http
      .post<IManagementResource>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(managementResource: IManagementResource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResource);
    return this.http
      .put<IManagementResource>(`${this.resourceUrl}/${getManagementResourceIdentifier(managementResource) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(managementResource: IManagementResource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResource);
    return this.http
      .patch<IManagementResource>(`${this.resourceUrl}/${getManagementResourceIdentifier(managementResource) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IManagementResource>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IManagementResource[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addManagementResourceToCollectionIfMissing(
    managementResourceCollection: IManagementResource[],
    ...managementResourcesToCheck: (IManagementResource | null | undefined)[]
  ): IManagementResource[] {
    const managementResources: IManagementResource[] = managementResourcesToCheck.filter(isPresent);
    if (managementResources.length > 0) {
      const managementResourceCollectionIdentifiers = managementResourceCollection.map(
        managementResourceItem => getManagementResourceIdentifier(managementResourceItem)!
      );
      const managementResourcesToAdd = managementResources.filter(managementResourceItem => {
        const managementResourceIdentifier = getManagementResourceIdentifier(managementResourceItem);
        if (managementResourceIdentifier == null || managementResourceCollectionIdentifiers.includes(managementResourceIdentifier)) {
          return false;
        }
        managementResourceCollectionIdentifiers.push(managementResourceIdentifier);
        return true;
      });
      return [...managementResourcesToAdd, ...managementResourceCollection];
    }
    return managementResourceCollection;
  }

  protected convertDateFromClient(managementResource: IManagementResource): IManagementResource {
    return Object.assign({}, managementResource, {
      dateop: managementResource.dateop?.isValid() ? managementResource.dateop.toJSON() : undefined,
      createdDate: managementResource.createdDate?.isValid() ? managementResource.createdDate.toJSON() : undefined,
      modifiedDate: managementResource.modifiedDate?.isValid() ? managementResource.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((managementResource: IManagementResource) => {
        managementResource.dateop = managementResource.dateop ? dayjs(managementResource.dateop) : undefined;
        managementResource.createdDate = managementResource.createdDate ? dayjs(managementResource.createdDate) : undefined;
        managementResource.modifiedDate = managementResource.modifiedDate ? dayjs(managementResource.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
