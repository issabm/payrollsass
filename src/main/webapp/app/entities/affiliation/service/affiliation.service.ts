import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffiliation, getAffiliationIdentifier } from '../affiliation.model';

export type EntityResponseType = HttpResponse<IAffiliation>;
export type EntityArrayResponseType = HttpResponse<IAffiliation[]>;

@Injectable({ providedIn: 'root' })
export class AffiliationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affiliations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(affiliation: IAffiliation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affiliation);
    return this.http
      .post<IAffiliation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(affiliation: IAffiliation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affiliation);
    return this.http
      .put<IAffiliation>(`${this.resourceUrl}/${getAffiliationIdentifier(affiliation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(affiliation: IAffiliation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affiliation);
    return this.http
      .patch<IAffiliation>(`${this.resourceUrl}/${getAffiliationIdentifier(affiliation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAffiliation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAffiliation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAffiliationToCollectionIfMissing(
    affiliationCollection: IAffiliation[],
    ...affiliationsToCheck: (IAffiliation | null | undefined)[]
  ): IAffiliation[] {
    const affiliations: IAffiliation[] = affiliationsToCheck.filter(isPresent);
    if (affiliations.length > 0) {
      const affiliationCollectionIdentifiers = affiliationCollection.map(affiliationItem => getAffiliationIdentifier(affiliationItem)!);
      const affiliationsToAdd = affiliations.filter(affiliationItem => {
        const affiliationIdentifier = getAffiliationIdentifier(affiliationItem);
        if (affiliationIdentifier == null || affiliationCollectionIdentifiers.includes(affiliationIdentifier)) {
          return false;
        }
        affiliationCollectionIdentifiers.push(affiliationIdentifier);
        return true;
      });
      return [...affiliationsToAdd, ...affiliationCollection];
    }
    return affiliationCollection;
  }

  protected convertDateFromClient(affiliation: IAffiliation): IAffiliation {
    return Object.assign({}, affiliation, {
      dateop: affiliation.dateop?.isValid() ? affiliation.dateop.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((affiliation: IAffiliation) => {
        affiliation.dateop = affiliation.dateop ? dayjs(affiliation.dateop) : undefined;
      });
    }
    return res;
  }
}
