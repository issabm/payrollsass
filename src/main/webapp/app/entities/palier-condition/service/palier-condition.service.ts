import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPalierCondition, getPalierConditionIdentifier } from '../palier-condition.model';

export type EntityResponseType = HttpResponse<IPalierCondition>;
export type EntityArrayResponseType = HttpResponse<IPalierCondition[]>;

@Injectable({ providedIn: 'root' })
export class PalierConditionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/palier-conditions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(palierCondition: IPalierCondition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierCondition);
    return this.http
      .post<IPalierCondition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(palierCondition: IPalierCondition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierCondition);
    return this.http
      .put<IPalierCondition>(`${this.resourceUrl}/${getPalierConditionIdentifier(palierCondition) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(palierCondition: IPalierCondition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierCondition);
    return this.http
      .patch<IPalierCondition>(`${this.resourceUrl}/${getPalierConditionIdentifier(palierCondition) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPalierCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPalierCondition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPalierConditionToCollectionIfMissing(
    palierConditionCollection: IPalierCondition[],
    ...palierConditionsToCheck: (IPalierCondition | null | undefined)[]
  ): IPalierCondition[] {
    const palierConditions: IPalierCondition[] = palierConditionsToCheck.filter(isPresent);
    if (palierConditions.length > 0) {
      const palierConditionCollectionIdentifiers = palierConditionCollection.map(
        palierConditionItem => getPalierConditionIdentifier(palierConditionItem)!
      );
      const palierConditionsToAdd = palierConditions.filter(palierConditionItem => {
        const palierConditionIdentifier = getPalierConditionIdentifier(palierConditionItem);
        if (palierConditionIdentifier == null || palierConditionCollectionIdentifiers.includes(palierConditionIdentifier)) {
          return false;
        }
        palierConditionCollectionIdentifiers.push(palierConditionIdentifier);
        return true;
      });
      return [...palierConditionsToAdd, ...palierConditionCollection];
    }
    return palierConditionCollection;
  }

  protected convertDateFromClient(palierCondition: IPalierCondition): IPalierCondition {
    return Object.assign({}, palierCondition, {
      dateop: palierCondition.dateop?.isValid() ? palierCondition.dateop.toJSON() : undefined,
      dateModif: palierCondition.dateModif?.isValid() ? palierCondition.dateModif.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.dateModif = res.body.dateModif ? dayjs(res.body.dateModif) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((palierCondition: IPalierCondition) => {
        palierCondition.dateop = palierCondition.dateop ? dayjs(palierCondition.dateop) : undefined;
        palierCondition.dateModif = palierCondition.dateModif ? dayjs(palierCondition.dateModif) : undefined;
      });
    }
    return res;
  }
}
