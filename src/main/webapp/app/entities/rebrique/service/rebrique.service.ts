import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRebrique, getRebriqueIdentifier } from '../rebrique.model';

export type EntityResponseType = HttpResponse<IRebrique>;
export type EntityArrayResponseType = HttpResponse<IRebrique[]>;

@Injectable({ providedIn: 'root' })
export class RebriqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rebriques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rebrique: IRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebrique);
    return this.http
      .post<IRebrique>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rebrique: IRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebrique);
    return this.http
      .put<IRebrique>(`${this.resourceUrl}/${getRebriqueIdentifier(rebrique) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rebrique: IRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebrique);
    return this.http
      .patch<IRebrique>(`${this.resourceUrl}/${getRebriqueIdentifier(rebrique) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRebrique>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRebrique[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRebriqueToCollectionIfMissing(rebriqueCollection: IRebrique[], ...rebriquesToCheck: (IRebrique | null | undefined)[]): IRebrique[] {
    const rebriques: IRebrique[] = rebriquesToCheck.filter(isPresent);
    if (rebriques.length > 0) {
      const rebriqueCollectionIdentifiers = rebriqueCollection.map(rebriqueItem => getRebriqueIdentifier(rebriqueItem)!);
      const rebriquesToAdd = rebriques.filter(rebriqueItem => {
        const rebriqueIdentifier = getRebriqueIdentifier(rebriqueItem);
        if (rebriqueIdentifier == null || rebriqueCollectionIdentifiers.includes(rebriqueIdentifier)) {
          return false;
        }
        rebriqueCollectionIdentifiers.push(rebriqueIdentifier);
        return true;
      });
      return [...rebriquesToAdd, ...rebriqueCollection];
    }
    return rebriqueCollection;
  }

  protected convertDateFromClient(rebrique: IRebrique): IRebrique {
    return Object.assign({}, rebrique, {
      dateSituation: rebrique.dateSituation?.isValid() ? rebrique.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: rebrique.dateop?.isValid() ? rebrique.dateop.toJSON() : undefined,
      createdDate: rebrique.createdDate?.isValid() ? rebrique.createdDate.toJSON() : undefined,
      modifiedDate: rebrique.modifiedDate?.isValid() ? rebrique.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rebrique: IRebrique) => {
        rebrique.dateSituation = rebrique.dateSituation ? dayjs(rebrique.dateSituation) : undefined;
        rebrique.dateop = rebrique.dateop ? dayjs(rebrique.dateop) : undefined;
        rebrique.createdDate = rebrique.createdDate ? dayjs(rebrique.createdDate) : undefined;
        rebrique.modifiedDate = rebrique.modifiedDate ? dayjs(rebrique.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
