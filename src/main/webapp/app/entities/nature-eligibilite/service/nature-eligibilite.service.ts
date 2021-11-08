import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureEligibilite, getNatureEligibiliteIdentifier } from '../nature-eligibilite.model';

export type EntityResponseType = HttpResponse<INatureEligibilite>;
export type EntityArrayResponseType = HttpResponse<INatureEligibilite[]>;

@Injectable({ providedIn: 'root' })
export class NatureEligibiliteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-eligibilites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureEligibilite: INatureEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureEligibilite);
    return this.http
      .post<INatureEligibilite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureEligibilite: INatureEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureEligibilite);
    return this.http
      .put<INatureEligibilite>(`${this.resourceUrl}/${getNatureEligibiliteIdentifier(natureEligibilite) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureEligibilite: INatureEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureEligibilite);
    return this.http
      .patch<INatureEligibilite>(`${this.resourceUrl}/${getNatureEligibiliteIdentifier(natureEligibilite) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureEligibilite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureEligibilite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureEligibiliteToCollectionIfMissing(
    natureEligibiliteCollection: INatureEligibilite[],
    ...natureEligibilitesToCheck: (INatureEligibilite | null | undefined)[]
  ): INatureEligibilite[] {
    const natureEligibilites: INatureEligibilite[] = natureEligibilitesToCheck.filter(isPresent);
    if (natureEligibilites.length > 0) {
      const natureEligibiliteCollectionIdentifiers = natureEligibiliteCollection.map(
        natureEligibiliteItem => getNatureEligibiliteIdentifier(natureEligibiliteItem)!
      );
      const natureEligibilitesToAdd = natureEligibilites.filter(natureEligibiliteItem => {
        const natureEligibiliteIdentifier = getNatureEligibiliteIdentifier(natureEligibiliteItem);
        if (natureEligibiliteIdentifier == null || natureEligibiliteCollectionIdentifiers.includes(natureEligibiliteIdentifier)) {
          return false;
        }
        natureEligibiliteCollectionIdentifiers.push(natureEligibiliteIdentifier);
        return true;
      });
      return [...natureEligibilitesToAdd, ...natureEligibiliteCollection];
    }
    return natureEligibiliteCollection;
  }

  protected convertDateFromClient(natureEligibilite: INatureEligibilite): INatureEligibilite {
    return Object.assign({}, natureEligibilite, {
      dateop: natureEligibilite.dateop?.isValid() ? natureEligibilite.dateop.toJSON() : undefined,
      createdDate: natureEligibilite.createdDate?.isValid() ? natureEligibilite.createdDate.toJSON() : undefined,
      modifiedDate: natureEligibilite.modifiedDate?.isValid() ? natureEligibilite.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureEligibilite: INatureEligibilite) => {
        natureEligibilite.dateop = natureEligibilite.dateop ? dayjs(natureEligibilite.dateop) : undefined;
        natureEligibilite.createdDate = natureEligibilite.createdDate ? dayjs(natureEligibilite.createdDate) : undefined;
        natureEligibilite.modifiedDate = natureEligibilite.modifiedDate ? dayjs(natureEligibilite.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
