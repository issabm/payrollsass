import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlateInfo, getPlateInfoIdentifier } from '../plate-info.model';

export type EntityResponseType = HttpResponse<IPlateInfo>;
export type EntityArrayResponseType = HttpResponse<IPlateInfo[]>;

@Injectable({ providedIn: 'root' })
export class PlateInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plate-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plateInfo: IPlateInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plateInfo);
    return this.http
      .post<IPlateInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plateInfo: IPlateInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plateInfo);
    return this.http
      .put<IPlateInfo>(`${this.resourceUrl}/${getPlateInfoIdentifier(plateInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(plateInfo: IPlateInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plateInfo);
    return this.http
      .patch<IPlateInfo>(`${this.resourceUrl}/${getPlateInfoIdentifier(plateInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlateInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlateInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPlateInfoToCollectionIfMissing(
    plateInfoCollection: IPlateInfo[],
    ...plateInfosToCheck: (IPlateInfo | null | undefined)[]
  ): IPlateInfo[] {
    const plateInfos: IPlateInfo[] = plateInfosToCheck.filter(isPresent);
    if (plateInfos.length > 0) {
      const plateInfoCollectionIdentifiers = plateInfoCollection.map(plateInfoItem => getPlateInfoIdentifier(plateInfoItem)!);
      const plateInfosToAdd = plateInfos.filter(plateInfoItem => {
        const plateInfoIdentifier = getPlateInfoIdentifier(plateInfoItem);
        if (plateInfoIdentifier == null || plateInfoCollectionIdentifiers.includes(plateInfoIdentifier)) {
          return false;
        }
        plateInfoCollectionIdentifiers.push(plateInfoIdentifier);
        return true;
      });
      return [...plateInfosToAdd, ...plateInfoCollection];
    }
    return plateInfoCollection;
  }

  protected convertDateFromClient(plateInfo: IPlateInfo): IPlateInfo {
    return Object.assign({}, plateInfo, {
      dateSituation: plateInfo.dateSituation?.isValid() ? plateInfo.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: plateInfo.dateop?.isValid() ? plateInfo.dateop.toJSON() : undefined,
      createdDate: plateInfo.createdDate?.isValid() ? plateInfo.createdDate.toJSON() : undefined,
      modifiedDate: plateInfo.modifiedDate?.isValid() ? plateInfo.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((plateInfo: IPlateInfo) => {
        plateInfo.dateSituation = plateInfo.dateSituation ? dayjs(plateInfo.dateSituation) : undefined;
        plateInfo.dateop = plateInfo.dateop ? dayjs(plateInfo.dateop) : undefined;
        plateInfo.createdDate = plateInfo.createdDate ? dayjs(plateInfo.createdDate) : undefined;
        plateInfo.modifiedDate = plateInfo.modifiedDate ? dayjs(plateInfo.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
