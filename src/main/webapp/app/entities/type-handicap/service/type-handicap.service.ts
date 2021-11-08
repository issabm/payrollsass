import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeHandicap, getTypeHandicapIdentifier } from '../type-handicap.model';

export type EntityResponseType = HttpResponse<ITypeHandicap>;
export type EntityArrayResponseType = HttpResponse<ITypeHandicap[]>;

@Injectable({ providedIn: 'root' })
export class TypeHandicapService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-handicaps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeHandicap: ITypeHandicap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeHandicap);
    return this.http
      .post<ITypeHandicap>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeHandicap: ITypeHandicap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeHandicap);
    return this.http
      .put<ITypeHandicap>(`${this.resourceUrl}/${getTypeHandicapIdentifier(typeHandicap) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(typeHandicap: ITypeHandicap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeHandicap);
    return this.http
      .patch<ITypeHandicap>(`${this.resourceUrl}/${getTypeHandicapIdentifier(typeHandicap) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeHandicap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeHandicap[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeHandicapToCollectionIfMissing(
    typeHandicapCollection: ITypeHandicap[],
    ...typeHandicapsToCheck: (ITypeHandicap | null | undefined)[]
  ): ITypeHandicap[] {
    const typeHandicaps: ITypeHandicap[] = typeHandicapsToCheck.filter(isPresent);
    if (typeHandicaps.length > 0) {
      const typeHandicapCollectionIdentifiers = typeHandicapCollection.map(
        typeHandicapItem => getTypeHandicapIdentifier(typeHandicapItem)!
      );
      const typeHandicapsToAdd = typeHandicaps.filter(typeHandicapItem => {
        const typeHandicapIdentifier = getTypeHandicapIdentifier(typeHandicapItem);
        if (typeHandicapIdentifier == null || typeHandicapCollectionIdentifiers.includes(typeHandicapIdentifier)) {
          return false;
        }
        typeHandicapCollectionIdentifiers.push(typeHandicapIdentifier);
        return true;
      });
      return [...typeHandicapsToAdd, ...typeHandicapCollection];
    }
    return typeHandicapCollection;
  }

  protected convertDateFromClient(typeHandicap: ITypeHandicap): ITypeHandicap {
    return Object.assign({}, typeHandicap, {
      dateop: typeHandicap.dateop?.isValid() ? typeHandicap.dateop.toJSON() : undefined,
      createdDate: typeHandicap.createdDate?.isValid() ? typeHandicap.createdDate.toJSON() : undefined,
      modifiedDate: typeHandicap.modifiedDate?.isValid() ? typeHandicap.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((typeHandicap: ITypeHandicap) => {
        typeHandicap.dateop = typeHandicap.dateop ? dayjs(typeHandicap.dateop) : undefined;
        typeHandicap.createdDate = typeHandicap.createdDate ? dayjs(typeHandicap.createdDate) : undefined;
        typeHandicap.modifiedDate = typeHandicap.modifiedDate ? dayjs(typeHandicap.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
