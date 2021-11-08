import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEligibiliteExclude, getEligibiliteExcludeIdentifier } from '../eligibilite-exclude.model';

export type EntityResponseType = HttpResponse<IEligibiliteExclude>;
export type EntityArrayResponseType = HttpResponse<IEligibiliteExclude[]>;

@Injectable({ providedIn: 'root' })
export class EligibiliteExcludeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/eligibilite-excludes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eligibiliteExclude: IEligibiliteExclude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibiliteExclude);
    return this.http
      .post<IEligibiliteExclude>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eligibiliteExclude: IEligibiliteExclude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibiliteExclude);
    return this.http
      .put<IEligibiliteExclude>(`${this.resourceUrl}/${getEligibiliteExcludeIdentifier(eligibiliteExclude) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(eligibiliteExclude: IEligibiliteExclude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibiliteExclude);
    return this.http
      .patch<IEligibiliteExclude>(`${this.resourceUrl}/${getEligibiliteExcludeIdentifier(eligibiliteExclude) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEligibiliteExclude>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEligibiliteExclude[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEligibiliteExcludeToCollectionIfMissing(
    eligibiliteExcludeCollection: IEligibiliteExclude[],
    ...eligibiliteExcludesToCheck: (IEligibiliteExclude | null | undefined)[]
  ): IEligibiliteExclude[] {
    const eligibiliteExcludes: IEligibiliteExclude[] = eligibiliteExcludesToCheck.filter(isPresent);
    if (eligibiliteExcludes.length > 0) {
      const eligibiliteExcludeCollectionIdentifiers = eligibiliteExcludeCollection.map(
        eligibiliteExcludeItem => getEligibiliteExcludeIdentifier(eligibiliteExcludeItem)!
      );
      const eligibiliteExcludesToAdd = eligibiliteExcludes.filter(eligibiliteExcludeItem => {
        const eligibiliteExcludeIdentifier = getEligibiliteExcludeIdentifier(eligibiliteExcludeItem);
        if (eligibiliteExcludeIdentifier == null || eligibiliteExcludeCollectionIdentifiers.includes(eligibiliteExcludeIdentifier)) {
          return false;
        }
        eligibiliteExcludeCollectionIdentifiers.push(eligibiliteExcludeIdentifier);
        return true;
      });
      return [...eligibiliteExcludesToAdd, ...eligibiliteExcludeCollection];
    }
    return eligibiliteExcludeCollection;
  }

  protected convertDateFromClient(eligibiliteExclude: IEligibiliteExclude): IEligibiliteExclude {
    return Object.assign({}, eligibiliteExclude, {
      dateop: eligibiliteExclude.dateop?.isValid() ? eligibiliteExclude.dateop.toJSON() : undefined,
      createdDate: eligibiliteExclude.createdDate?.isValid() ? eligibiliteExclude.createdDate.toJSON() : undefined,
      modifiedDate: eligibiliteExclude.modifiedDate?.isValid() ? eligibiliteExclude.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((eligibiliteExclude: IEligibiliteExclude) => {
        eligibiliteExclude.dateop = eligibiliteExclude.dateop ? dayjs(eligibiliteExclude.dateop) : undefined;
        eligibiliteExclude.createdDate = eligibiliteExclude.createdDate ? dayjs(eligibiliteExclude.createdDate) : undefined;
        eligibiliteExclude.modifiedDate = eligibiliteExclude.modifiedDate ? dayjs(eligibiliteExclude.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
