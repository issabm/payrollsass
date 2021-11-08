import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureAbsence, getNatureAbsenceIdentifier } from '../nature-absence.model';

export type EntityResponseType = HttpResponse<INatureAbsence>;
export type EntityArrayResponseType = HttpResponse<INatureAbsence[]>;

@Injectable({ providedIn: 'root' })
export class NatureAbsenceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-absences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureAbsence: INatureAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsence);
    return this.http
      .post<INatureAbsence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureAbsence: INatureAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsence);
    return this.http
      .put<INatureAbsence>(`${this.resourceUrl}/${getNatureAbsenceIdentifier(natureAbsence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureAbsence: INatureAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsence);
    return this.http
      .patch<INatureAbsence>(`${this.resourceUrl}/${getNatureAbsenceIdentifier(natureAbsence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureAbsence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureAbsenceToCollectionIfMissing(
    natureAbsenceCollection: INatureAbsence[],
    ...natureAbsencesToCheck: (INatureAbsence | null | undefined)[]
  ): INatureAbsence[] {
    const natureAbsences: INatureAbsence[] = natureAbsencesToCheck.filter(isPresent);
    if (natureAbsences.length > 0) {
      const natureAbsenceCollectionIdentifiers = natureAbsenceCollection.map(
        natureAbsenceItem => getNatureAbsenceIdentifier(natureAbsenceItem)!
      );
      const natureAbsencesToAdd = natureAbsences.filter(natureAbsenceItem => {
        const natureAbsenceIdentifier = getNatureAbsenceIdentifier(natureAbsenceItem);
        if (natureAbsenceIdentifier == null || natureAbsenceCollectionIdentifiers.includes(natureAbsenceIdentifier)) {
          return false;
        }
        natureAbsenceCollectionIdentifiers.push(natureAbsenceIdentifier);
        return true;
      });
      return [...natureAbsencesToAdd, ...natureAbsenceCollection];
    }
    return natureAbsenceCollection;
  }

  protected convertDateFromClient(natureAbsence: INatureAbsence): INatureAbsence {
    return Object.assign({}, natureAbsence, {
      dateop: natureAbsence.dateop?.isValid() ? natureAbsence.dateop.toJSON() : undefined,
      createdDate: natureAbsence.createdDate?.isValid() ? natureAbsence.createdDate.toJSON() : undefined,
      modifiedDate: natureAbsence.modifiedDate?.isValid() ? natureAbsence.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureAbsence: INatureAbsence) => {
        natureAbsence.dateop = natureAbsence.dateop ? dayjs(natureAbsence.dateop) : undefined;
        natureAbsence.createdDate = natureAbsence.createdDate ? dayjs(natureAbsence.createdDate) : undefined;
        natureAbsence.modifiedDate = natureAbsence.modifiedDate ? dayjs(natureAbsence.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
