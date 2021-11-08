import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFrequence, getFrequenceIdentifier } from '../frequence.model';

export type EntityResponseType = HttpResponse<IFrequence>;
export type EntityArrayResponseType = HttpResponse<IFrequence[]>;

@Injectable({ providedIn: 'root' })
export class FrequenceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/frequences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(frequence: IFrequence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(frequence);
    return this.http
      .post<IFrequence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(frequence: IFrequence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(frequence);
    return this.http
      .put<IFrequence>(`${this.resourceUrl}/${getFrequenceIdentifier(frequence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(frequence: IFrequence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(frequence);
    return this.http
      .patch<IFrequence>(`${this.resourceUrl}/${getFrequenceIdentifier(frequence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFrequence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFrequence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFrequenceToCollectionIfMissing(
    frequenceCollection: IFrequence[],
    ...frequencesToCheck: (IFrequence | null | undefined)[]
  ): IFrequence[] {
    const frequences: IFrequence[] = frequencesToCheck.filter(isPresent);
    if (frequences.length > 0) {
      const frequenceCollectionIdentifiers = frequenceCollection.map(frequenceItem => getFrequenceIdentifier(frequenceItem)!);
      const frequencesToAdd = frequences.filter(frequenceItem => {
        const frequenceIdentifier = getFrequenceIdentifier(frequenceItem);
        if (frequenceIdentifier == null || frequenceCollectionIdentifiers.includes(frequenceIdentifier)) {
          return false;
        }
        frequenceCollectionIdentifiers.push(frequenceIdentifier);
        return true;
      });
      return [...frequencesToAdd, ...frequenceCollection];
    }
    return frequenceCollection;
  }

  protected convertDateFromClient(frequence: IFrequence): IFrequence {
    return Object.assign({}, frequence, {
      dateop: frequence.dateop?.isValid() ? frequence.dateop.toJSON() : undefined,
      createdDate: frequence.createdDate?.isValid() ? frequence.createdDate.toJSON() : undefined,
      modifiedDate: frequence.modifiedDate?.isValid() ? frequence.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((frequence: IFrequence) => {
        frequence.dateop = frequence.dateop ? dayjs(frequence.dateop) : undefined;
        frequence.createdDate = frequence.createdDate ? dayjs(frequence.createdDate) : undefined;
        frequence.modifiedDate = frequence.modifiedDate ? dayjs(frequence.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
