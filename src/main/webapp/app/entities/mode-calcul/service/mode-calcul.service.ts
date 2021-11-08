import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModeCalcul, getModeCalculIdentifier } from '../mode-calcul.model';

export type EntityResponseType = HttpResponse<IModeCalcul>;
export type EntityArrayResponseType = HttpResponse<IModeCalcul[]>;

@Injectable({ providedIn: 'root' })
export class ModeCalculService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-calculs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(modeCalcul: IModeCalcul): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeCalcul);
    return this.http
      .post<IModeCalcul>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(modeCalcul: IModeCalcul): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeCalcul);
    return this.http
      .put<IModeCalcul>(`${this.resourceUrl}/${getModeCalculIdentifier(modeCalcul) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(modeCalcul: IModeCalcul): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeCalcul);
    return this.http
      .patch<IModeCalcul>(`${this.resourceUrl}/${getModeCalculIdentifier(modeCalcul) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModeCalcul>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModeCalcul[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addModeCalculToCollectionIfMissing(
    modeCalculCollection: IModeCalcul[],
    ...modeCalculsToCheck: (IModeCalcul | null | undefined)[]
  ): IModeCalcul[] {
    const modeCalculs: IModeCalcul[] = modeCalculsToCheck.filter(isPresent);
    if (modeCalculs.length > 0) {
      const modeCalculCollectionIdentifiers = modeCalculCollection.map(modeCalculItem => getModeCalculIdentifier(modeCalculItem)!);
      const modeCalculsToAdd = modeCalculs.filter(modeCalculItem => {
        const modeCalculIdentifier = getModeCalculIdentifier(modeCalculItem);
        if (modeCalculIdentifier == null || modeCalculCollectionIdentifiers.includes(modeCalculIdentifier)) {
          return false;
        }
        modeCalculCollectionIdentifiers.push(modeCalculIdentifier);
        return true;
      });
      return [...modeCalculsToAdd, ...modeCalculCollection];
    }
    return modeCalculCollection;
  }

  protected convertDateFromClient(modeCalcul: IModeCalcul): IModeCalcul {
    return Object.assign({}, modeCalcul, {
      dateop: modeCalcul.dateop?.isValid() ? modeCalcul.dateop.toJSON() : undefined,
      createdDate: modeCalcul.createdDate?.isValid() ? modeCalcul.createdDate.toJSON() : undefined,
      modifiedDate: modeCalcul.modifiedDate?.isValid() ? modeCalcul.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((modeCalcul: IModeCalcul) => {
        modeCalcul.dateop = modeCalcul.dateop ? dayjs(modeCalcul.dateop) : undefined;
        modeCalcul.createdDate = modeCalcul.createdDate ? dayjs(modeCalcul.createdDate) : undefined;
        modeCalcul.modifiedDate = modeCalcul.modifiedDate ? dayjs(modeCalcul.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
