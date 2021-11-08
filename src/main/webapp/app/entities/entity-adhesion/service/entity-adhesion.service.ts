import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntityAdhesion, getEntityAdhesionIdentifier } from '../entity-adhesion.model';

export type EntityResponseType = HttpResponse<IEntityAdhesion>;
export type EntityArrayResponseType = HttpResponse<IEntityAdhesion[]>;

@Injectable({ providedIn: 'root' })
export class EntityAdhesionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entity-adhesions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(entityAdhesion: IEntityAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityAdhesion);
    return this.http
      .post<IEntityAdhesion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(entityAdhesion: IEntityAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityAdhesion);
    return this.http
      .put<IEntityAdhesion>(`${this.resourceUrl}/${getEntityAdhesionIdentifier(entityAdhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(entityAdhesion: IEntityAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityAdhesion);
    return this.http
      .patch<IEntityAdhesion>(`${this.resourceUrl}/${getEntityAdhesionIdentifier(entityAdhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEntityAdhesion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEntityAdhesion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEntityAdhesionToCollectionIfMissing(
    entityAdhesionCollection: IEntityAdhesion[],
    ...entityAdhesionsToCheck: (IEntityAdhesion | null | undefined)[]
  ): IEntityAdhesion[] {
    const entityAdhesions: IEntityAdhesion[] = entityAdhesionsToCheck.filter(isPresent);
    if (entityAdhesions.length > 0) {
      const entityAdhesionCollectionIdentifiers = entityAdhesionCollection.map(
        entityAdhesionItem => getEntityAdhesionIdentifier(entityAdhesionItem)!
      );
      const entityAdhesionsToAdd = entityAdhesions.filter(entityAdhesionItem => {
        const entityAdhesionIdentifier = getEntityAdhesionIdentifier(entityAdhesionItem);
        if (entityAdhesionIdentifier == null || entityAdhesionCollectionIdentifiers.includes(entityAdhesionIdentifier)) {
          return false;
        }
        entityAdhesionCollectionIdentifiers.push(entityAdhesionIdentifier);
        return true;
      });
      return [...entityAdhesionsToAdd, ...entityAdhesionCollection];
    }
    return entityAdhesionCollection;
  }

  protected convertDateFromClient(entityAdhesion: IEntityAdhesion): IEntityAdhesion {
    return Object.assign({}, entityAdhesion, {
      dateop: entityAdhesion.dateop?.isValid() ? entityAdhesion.dateop.toJSON() : undefined,
      createdDate: entityAdhesion.createdDate?.isValid() ? entityAdhesion.createdDate.toJSON() : undefined,
      modifiedDate: entityAdhesion.modifiedDate?.isValid() ? entityAdhesion.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((entityAdhesion: IEntityAdhesion) => {
        entityAdhesion.dateop = entityAdhesion.dateop ? dayjs(entityAdhesion.dateop) : undefined;
        entityAdhesion.createdDate = entityAdhesion.createdDate ? dayjs(entityAdhesion.createdDate) : undefined;
        entityAdhesion.modifiedDate = entityAdhesion.modifiedDate ? dayjs(entityAdhesion.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
