import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConcerne, getConcerneIdentifier } from '../concerne.model';

export type EntityResponseType = HttpResponse<IConcerne>;
export type EntityArrayResponseType = HttpResponse<IConcerne[]>;

@Injectable({ providedIn: 'root' })
export class ConcerneService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/concernes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(concerne: IConcerne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concerne);
    return this.http
      .post<IConcerne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(concerne: IConcerne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concerne);
    return this.http
      .put<IConcerne>(`${this.resourceUrl}/${getConcerneIdentifier(concerne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(concerne: IConcerne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concerne);
    return this.http
      .patch<IConcerne>(`${this.resourceUrl}/${getConcerneIdentifier(concerne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConcerne>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConcerne[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConcerneToCollectionIfMissing(concerneCollection: IConcerne[], ...concernesToCheck: (IConcerne | null | undefined)[]): IConcerne[] {
    const concernes: IConcerne[] = concernesToCheck.filter(isPresent);
    if (concernes.length > 0) {
      const concerneCollectionIdentifiers = concerneCollection.map(concerneItem => getConcerneIdentifier(concerneItem)!);
      const concernesToAdd = concernes.filter(concerneItem => {
        const concerneIdentifier = getConcerneIdentifier(concerneItem);
        if (concerneIdentifier == null || concerneCollectionIdentifiers.includes(concerneIdentifier)) {
          return false;
        }
        concerneCollectionIdentifiers.push(concerneIdentifier);
        return true;
      });
      return [...concernesToAdd, ...concerneCollection];
    }
    return concerneCollection;
  }

  protected convertDateFromClient(concerne: IConcerne): IConcerne {
    return Object.assign({}, concerne, {
      dateop: concerne.dateop?.isValid() ? concerne.dateop.toJSON() : undefined,
      createdDate: concerne.createdDate?.isValid() ? concerne.createdDate.toJSON() : undefined,
      modifiedDate: concerne.modifiedDate?.isValid() ? concerne.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((concerne: IConcerne) => {
        concerne.dateop = concerne.dateop ? dayjs(concerne.dateop) : undefined;
        concerne.createdDate = concerne.createdDate ? dayjs(concerne.createdDate) : undefined;
        concerne.modifiedDate = concerne.modifiedDate ? dayjs(concerne.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
