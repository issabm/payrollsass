import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITargetEligible, getTargetEligibleIdentifier } from '../target-eligible.model';

export type EntityResponseType = HttpResponse<ITargetEligible>;
export type EntityArrayResponseType = HttpResponse<ITargetEligible[]>;

@Injectable({ providedIn: 'root' })
export class TargetEligibleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/target-eligibles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(targetEligible: ITargetEligible): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(targetEligible);
    return this.http
      .post<ITargetEligible>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(targetEligible: ITargetEligible): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(targetEligible);
    return this.http
      .put<ITargetEligible>(`${this.resourceUrl}/${getTargetEligibleIdentifier(targetEligible) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(targetEligible: ITargetEligible): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(targetEligible);
    return this.http
      .patch<ITargetEligible>(`${this.resourceUrl}/${getTargetEligibleIdentifier(targetEligible) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITargetEligible>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITargetEligible[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTargetEligibleToCollectionIfMissing(
    targetEligibleCollection: ITargetEligible[],
    ...targetEligiblesToCheck: (ITargetEligible | null | undefined)[]
  ): ITargetEligible[] {
    const targetEligibles: ITargetEligible[] = targetEligiblesToCheck.filter(isPresent);
    if (targetEligibles.length > 0) {
      const targetEligibleCollectionIdentifiers = targetEligibleCollection.map(
        targetEligibleItem => getTargetEligibleIdentifier(targetEligibleItem)!
      );
      const targetEligiblesToAdd = targetEligibles.filter(targetEligibleItem => {
        const targetEligibleIdentifier = getTargetEligibleIdentifier(targetEligibleItem);
        if (targetEligibleIdentifier == null || targetEligibleCollectionIdentifiers.includes(targetEligibleIdentifier)) {
          return false;
        }
        targetEligibleCollectionIdentifiers.push(targetEligibleIdentifier);
        return true;
      });
      return [...targetEligiblesToAdd, ...targetEligibleCollection];
    }
    return targetEligibleCollection;
  }

  protected convertDateFromClient(targetEligible: ITargetEligible): ITargetEligible {
    return Object.assign({}, targetEligible, {
      dateop: targetEligible.dateop?.isValid() ? targetEligible.dateop.toJSON() : undefined,
      createdDate: targetEligible.createdDate?.isValid() ? targetEligible.createdDate.toJSON() : undefined,
      modifiedDate: targetEligible.modifiedDate?.isValid() ? targetEligible.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((targetEligible: ITargetEligible) => {
        targetEligible.dateop = targetEligible.dateop ? dayjs(targetEligible.dateop) : undefined;
        targetEligible.createdDate = targetEligible.createdDate ? dayjs(targetEligible.createdDate) : undefined;
        targetEligible.modifiedDate = targetEligible.modifiedDate ? dayjs(targetEligible.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
