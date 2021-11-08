import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeIdentite, getTypeIdentiteIdentifier } from '../type-identite.model';

export type EntityResponseType = HttpResponse<ITypeIdentite>;
export type EntityArrayResponseType = HttpResponse<ITypeIdentite[]>;

@Injectable({ providedIn: 'root' })
export class TypeIdentiteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-identites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeIdentite: ITypeIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeIdentite);
    return this.http
      .post<ITypeIdentite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeIdentite: ITypeIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeIdentite);
    return this.http
      .put<ITypeIdentite>(`${this.resourceUrl}/${getTypeIdentiteIdentifier(typeIdentite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(typeIdentite: ITypeIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeIdentite);
    return this.http
      .patch<ITypeIdentite>(`${this.resourceUrl}/${getTypeIdentiteIdentifier(typeIdentite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeIdentite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeIdentite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeIdentiteToCollectionIfMissing(
    typeIdentiteCollection: ITypeIdentite[],
    ...typeIdentitesToCheck: (ITypeIdentite | null | undefined)[]
  ): ITypeIdentite[] {
    const typeIdentites: ITypeIdentite[] = typeIdentitesToCheck.filter(isPresent);
    if (typeIdentites.length > 0) {
      const typeIdentiteCollectionIdentifiers = typeIdentiteCollection.map(
        typeIdentiteItem => getTypeIdentiteIdentifier(typeIdentiteItem)!
      );
      const typeIdentitesToAdd = typeIdentites.filter(typeIdentiteItem => {
        const typeIdentiteIdentifier = getTypeIdentiteIdentifier(typeIdentiteItem);
        if (typeIdentiteIdentifier == null || typeIdentiteCollectionIdentifiers.includes(typeIdentiteIdentifier)) {
          return false;
        }
        typeIdentiteCollectionIdentifiers.push(typeIdentiteIdentifier);
        return true;
      });
      return [...typeIdentitesToAdd, ...typeIdentiteCollection];
    }
    return typeIdentiteCollection;
  }

  protected convertDateFromClient(typeIdentite: ITypeIdentite): ITypeIdentite {
    return Object.assign({}, typeIdentite, {
      dateop: typeIdentite.dateop?.isValid() ? typeIdentite.dateop.toJSON() : undefined,
      createdDate: typeIdentite.createdDate?.isValid() ? typeIdentite.createdDate.toJSON() : undefined,
      modifiedDate: typeIdentite.modifiedDate?.isValid() ? typeIdentite.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((typeIdentite: ITypeIdentite) => {
        typeIdentite.dateop = typeIdentite.dateop ? dayjs(typeIdentite.dateop) : undefined;
        typeIdentite.createdDate = typeIdentite.createdDate ? dayjs(typeIdentite.createdDate) : undefined;
        typeIdentite.modifiedDate = typeIdentite.modifiedDate ? dayjs(typeIdentite.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
