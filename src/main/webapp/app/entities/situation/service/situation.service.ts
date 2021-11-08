import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISituation, getSituationIdentifier } from '../situation.model';

export type EntityResponseType = HttpResponse<ISituation>;
export type EntityArrayResponseType = HttpResponse<ISituation[]>;

@Injectable({ providedIn: 'root' })
export class SituationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/situations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .post<ISituation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .put<ISituation>(`${this.resourceUrl}/${getSituationIdentifier(situation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .patch<ISituation>(`${this.resourceUrl}/${getSituationIdentifier(situation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISituation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISituation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSituationToCollectionIfMissing(
    situationCollection: ISituation[],
    ...situationsToCheck: (ISituation | null | undefined)[]
  ): ISituation[] {
    const situations: ISituation[] = situationsToCheck.filter(isPresent);
    if (situations.length > 0) {
      const situationCollectionIdentifiers = situationCollection.map(situationItem => getSituationIdentifier(situationItem)!);
      const situationsToAdd = situations.filter(situationItem => {
        const situationIdentifier = getSituationIdentifier(situationItem);
        if (situationIdentifier == null || situationCollectionIdentifiers.includes(situationIdentifier)) {
          return false;
        }
        situationCollectionIdentifiers.push(situationIdentifier);
        return true;
      });
      return [...situationsToAdd, ...situationCollection];
    }
    return situationCollection;
  }

  protected convertDateFromClient(situation: ISituation): ISituation {
    return Object.assign({}, situation, {
      dateop: situation.dateop?.isValid() ? situation.dateop.toJSON() : undefined,
      createdDate: situation.createdDate?.isValid() ? situation.createdDate.toJSON() : undefined,
      modifiedDate: situation.modifiedDate?.isValid() ? situation.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((situation: ISituation) => {
        situation.dateop = situation.dateop ? dayjs(situation.dateop) : undefined;
        situation.createdDate = situation.createdDate ? dayjs(situation.createdDate) : undefined;
        situation.modifiedDate = situation.modifiedDate ? dayjs(situation.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
