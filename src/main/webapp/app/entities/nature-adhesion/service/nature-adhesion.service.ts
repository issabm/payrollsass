import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureAdhesion, getNatureAdhesionIdentifier } from '../nature-adhesion.model';

export type EntityResponseType = HttpResponse<INatureAdhesion>;
export type EntityArrayResponseType = HttpResponse<INatureAdhesion[]>;

@Injectable({ providedIn: 'root' })
export class NatureAdhesionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-adhesions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureAdhesion: INatureAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAdhesion);
    return this.http
      .post<INatureAdhesion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureAdhesion: INatureAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAdhesion);
    return this.http
      .put<INatureAdhesion>(`${this.resourceUrl}/${getNatureAdhesionIdentifier(natureAdhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureAdhesion: INatureAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAdhesion);
    return this.http
      .patch<INatureAdhesion>(`${this.resourceUrl}/${getNatureAdhesionIdentifier(natureAdhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureAdhesion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureAdhesion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureAdhesionToCollectionIfMissing(
    natureAdhesionCollection: INatureAdhesion[],
    ...natureAdhesionsToCheck: (INatureAdhesion | null | undefined)[]
  ): INatureAdhesion[] {
    const natureAdhesions: INatureAdhesion[] = natureAdhesionsToCheck.filter(isPresent);
    if (natureAdhesions.length > 0) {
      const natureAdhesionCollectionIdentifiers = natureAdhesionCollection.map(
        natureAdhesionItem => getNatureAdhesionIdentifier(natureAdhesionItem)!
      );
      const natureAdhesionsToAdd = natureAdhesions.filter(natureAdhesionItem => {
        const natureAdhesionIdentifier = getNatureAdhesionIdentifier(natureAdhesionItem);
        if (natureAdhesionIdentifier == null || natureAdhesionCollectionIdentifiers.includes(natureAdhesionIdentifier)) {
          return false;
        }
        natureAdhesionCollectionIdentifiers.push(natureAdhesionIdentifier);
        return true;
      });
      return [...natureAdhesionsToAdd, ...natureAdhesionCollection];
    }
    return natureAdhesionCollection;
  }

  protected convertDateFromClient(natureAdhesion: INatureAdhesion): INatureAdhesion {
    return Object.assign({}, natureAdhesion, {
      dateop: natureAdhesion.dateop?.isValid() ? natureAdhesion.dateop.toJSON() : undefined,
      createdDate: natureAdhesion.createdDate?.isValid() ? natureAdhesion.createdDate.toJSON() : undefined,
      modifiedDate: natureAdhesion.modifiedDate?.isValid() ? natureAdhesion.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureAdhesion: INatureAdhesion) => {
        natureAdhesion.dateop = natureAdhesion.dateop ? dayjs(natureAdhesion.dateop) : undefined;
        natureAdhesion.createdDate = natureAdhesion.createdDate ? dayjs(natureAdhesion.createdDate) : undefined;
        natureAdhesion.modifiedDate = natureAdhesion.modifiedDate ? dayjs(natureAdhesion.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
