import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISens, getSensIdentifier } from '../sens.model';

export type EntityResponseType = HttpResponse<ISens>;
export type EntityArrayResponseType = HttpResponse<ISens[]>;

@Injectable({ providedIn: 'root' })
export class SensService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sens: ISens): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sens);
    return this.http
      .post<ISens>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sens: ISens): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sens);
    return this.http
      .put<ISens>(`${this.resourceUrl}/${getSensIdentifier(sens) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sens: ISens): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sens);
    return this.http
      .patch<ISens>(`${this.resourceUrl}/${getSensIdentifier(sens) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISens>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISens[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSensToCollectionIfMissing(sensCollection: ISens[], ...sensToCheck: (ISens | null | undefined)[]): ISens[] {
    const sens: ISens[] = sensToCheck.filter(isPresent);
    if (sens.length > 0) {
      const sensCollectionIdentifiers = sensCollection.map(sensItem => getSensIdentifier(sensItem)!);
      const sensToAdd = sens.filter(sensItem => {
        const sensIdentifier = getSensIdentifier(sensItem);
        if (sensIdentifier == null || sensCollectionIdentifiers.includes(sensIdentifier)) {
          return false;
        }
        sensCollectionIdentifiers.push(sensIdentifier);
        return true;
      });
      return [...sensToAdd, ...sensCollection];
    }
    return sensCollection;
  }

  protected convertDateFromClient(sens: ISens): ISens {
    return Object.assign({}, sens, {
      dateop: sens.dateop?.isValid() ? sens.dateop.toJSON() : undefined,
      createdDate: sens.createdDate?.isValid() ? sens.createdDate.toJSON() : undefined,
      modifiedDate: sens.modifiedDate?.isValid() ? sens.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((sens: ISens) => {
        sens.dateop = sens.dateop ? dayjs(sens.dateop) : undefined;
        sens.createdDate = sens.createdDate ? dayjs(sens.createdDate) : undefined;
        sens.modifiedDate = sens.modifiedDate ? dayjs(sens.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
