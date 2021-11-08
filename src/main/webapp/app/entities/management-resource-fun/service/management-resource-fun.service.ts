import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IManagementResourceFun, getManagementResourceFunIdentifier } from '../management-resource-fun.model';

export type EntityResponseType = HttpResponse<IManagementResourceFun>;
export type EntityArrayResponseType = HttpResponse<IManagementResourceFun[]>;

@Injectable({ providedIn: 'root' })
export class ManagementResourceFunService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/management-resource-funs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(managementResourceFun: IManagementResourceFun): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceFun);
    return this.http
      .post<IManagementResourceFun>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(managementResourceFun: IManagementResourceFun): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceFun);
    return this.http
      .put<IManagementResourceFun>(`${this.resourceUrl}/${getManagementResourceFunIdentifier(managementResourceFun) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(managementResourceFun: IManagementResourceFun): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceFun);
    return this.http
      .patch<IManagementResourceFun>(`${this.resourceUrl}/${getManagementResourceFunIdentifier(managementResourceFun) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IManagementResourceFun>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IManagementResourceFun[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addManagementResourceFunToCollectionIfMissing(
    managementResourceFunCollection: IManagementResourceFun[],
    ...managementResourceFunsToCheck: (IManagementResourceFun | null | undefined)[]
  ): IManagementResourceFun[] {
    const managementResourceFuns: IManagementResourceFun[] = managementResourceFunsToCheck.filter(isPresent);
    if (managementResourceFuns.length > 0) {
      const managementResourceFunCollectionIdentifiers = managementResourceFunCollection.map(
        managementResourceFunItem => getManagementResourceFunIdentifier(managementResourceFunItem)!
      );
      const managementResourceFunsToAdd = managementResourceFuns.filter(managementResourceFunItem => {
        const managementResourceFunIdentifier = getManagementResourceFunIdentifier(managementResourceFunItem);
        if (
          managementResourceFunIdentifier == null ||
          managementResourceFunCollectionIdentifiers.includes(managementResourceFunIdentifier)
        ) {
          return false;
        }
        managementResourceFunCollectionIdentifiers.push(managementResourceFunIdentifier);
        return true;
      });
      return [...managementResourceFunsToAdd, ...managementResourceFunCollection];
    }
    return managementResourceFunCollection;
  }

  protected convertDateFromClient(managementResourceFun: IManagementResourceFun): IManagementResourceFun {
    return Object.assign({}, managementResourceFun, {
      dateop: managementResourceFun.dateop?.isValid() ? managementResourceFun.dateop.toJSON() : undefined,
      createdDate: managementResourceFun.createdDate?.isValid() ? managementResourceFun.createdDate.toJSON() : undefined,
      modifiedDate: managementResourceFun.modifiedDate?.isValid() ? managementResourceFun.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((managementResourceFun: IManagementResourceFun) => {
        managementResourceFun.dateop = managementResourceFun.dateop ? dayjs(managementResourceFun.dateop) : undefined;
        managementResourceFun.createdDate = managementResourceFun.createdDate ? dayjs(managementResourceFun.createdDate) : undefined;
        managementResourceFun.modifiedDate = managementResourceFun.modifiedDate ? dayjs(managementResourceFun.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
