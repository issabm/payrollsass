import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISituationFamiliale, getSituationFamilialeIdentifier } from '../situation-familiale.model';

export type EntityResponseType = HttpResponse<ISituationFamiliale>;
export type EntityArrayResponseType = HttpResponse<ISituationFamiliale[]>;

@Injectable({ providedIn: 'root' })
export class SituationFamilialeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/situation-familiales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(situationFamiliale: ISituationFamiliale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situationFamiliale);
    return this.http
      .post<ISituationFamiliale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(situationFamiliale: ISituationFamiliale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situationFamiliale);
    return this.http
      .put<ISituationFamiliale>(`${this.resourceUrl}/${getSituationFamilialeIdentifier(situationFamiliale) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(situationFamiliale: ISituationFamiliale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situationFamiliale);
    return this.http
      .patch<ISituationFamiliale>(`${this.resourceUrl}/${getSituationFamilialeIdentifier(situationFamiliale) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISituationFamiliale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISituationFamiliale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSituationFamilialeToCollectionIfMissing(
    situationFamilialeCollection: ISituationFamiliale[],
    ...situationFamilialesToCheck: (ISituationFamiliale | null | undefined)[]
  ): ISituationFamiliale[] {
    const situationFamiliales: ISituationFamiliale[] = situationFamilialesToCheck.filter(isPresent);
    if (situationFamiliales.length > 0) {
      const situationFamilialeCollectionIdentifiers = situationFamilialeCollection.map(
        situationFamilialeItem => getSituationFamilialeIdentifier(situationFamilialeItem)!
      );
      const situationFamilialesToAdd = situationFamiliales.filter(situationFamilialeItem => {
        const situationFamilialeIdentifier = getSituationFamilialeIdentifier(situationFamilialeItem);
        if (situationFamilialeIdentifier == null || situationFamilialeCollectionIdentifiers.includes(situationFamilialeIdentifier)) {
          return false;
        }
        situationFamilialeCollectionIdentifiers.push(situationFamilialeIdentifier);
        return true;
      });
      return [...situationFamilialesToAdd, ...situationFamilialeCollection];
    }
    return situationFamilialeCollection;
  }

  protected convertDateFromClient(situationFamiliale: ISituationFamiliale): ISituationFamiliale {
    return Object.assign({}, situationFamiliale, {
      dateop: situationFamiliale.dateop?.isValid() ? situationFamiliale.dateop.toJSON() : undefined,
      createdDate: situationFamiliale.createdDate?.isValid() ? situationFamiliale.createdDate.toJSON() : undefined,
      modifiedDate: situationFamiliale.modifiedDate?.isValid() ? situationFamiliale.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((situationFamiliale: ISituationFamiliale) => {
        situationFamiliale.dateop = situationFamiliale.dateop ? dayjs(situationFamiliale.dateop) : undefined;
        situationFamiliale.createdDate = situationFamiliale.createdDate ? dayjs(situationFamiliale.createdDate) : undefined;
        situationFamiliale.modifiedDate = situationFamiliale.modifiedDate ? dayjs(situationFamiliale.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
