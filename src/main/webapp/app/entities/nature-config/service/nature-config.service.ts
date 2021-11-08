import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureConfig, getNatureConfigIdentifier } from '../nature-config.model';

export type EntityResponseType = HttpResponse<INatureConfig>;
export type EntityArrayResponseType = HttpResponse<INatureConfig[]>;

@Injectable({ providedIn: 'root' })
export class NatureConfigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-configs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureConfig: INatureConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureConfig);
    return this.http
      .post<INatureConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureConfig: INatureConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureConfig);
    return this.http
      .put<INatureConfig>(`${this.resourceUrl}/${getNatureConfigIdentifier(natureConfig) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureConfig: INatureConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureConfig);
    return this.http
      .patch<INatureConfig>(`${this.resourceUrl}/${getNatureConfigIdentifier(natureConfig) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureConfigToCollectionIfMissing(
    natureConfigCollection: INatureConfig[],
    ...natureConfigsToCheck: (INatureConfig | null | undefined)[]
  ): INatureConfig[] {
    const natureConfigs: INatureConfig[] = natureConfigsToCheck.filter(isPresent);
    if (natureConfigs.length > 0) {
      const natureConfigCollectionIdentifiers = natureConfigCollection.map(
        natureConfigItem => getNatureConfigIdentifier(natureConfigItem)!
      );
      const natureConfigsToAdd = natureConfigs.filter(natureConfigItem => {
        const natureConfigIdentifier = getNatureConfigIdentifier(natureConfigItem);
        if (natureConfigIdentifier == null || natureConfigCollectionIdentifiers.includes(natureConfigIdentifier)) {
          return false;
        }
        natureConfigCollectionIdentifiers.push(natureConfigIdentifier);
        return true;
      });
      return [...natureConfigsToAdd, ...natureConfigCollection];
    }
    return natureConfigCollection;
  }

  protected convertDateFromClient(natureConfig: INatureConfig): INatureConfig {
    return Object.assign({}, natureConfig, {
      dateop: natureConfig.dateop?.isValid() ? natureConfig.dateop.toJSON() : undefined,
      createdDate: natureConfig.createdDate?.isValid() ? natureConfig.createdDate.toJSON() : undefined,
      modifiedDate: natureConfig.modifiedDate?.isValid() ? natureConfig.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureConfig: INatureConfig) => {
        natureConfig.dateop = natureConfig.dateop ? dayjs(natureConfig.dateop) : undefined;
        natureConfig.createdDate = natureConfig.createdDate ? dayjs(natureConfig.createdDate) : undefined;
        natureConfig.modifiedDate = natureConfig.modifiedDate ? dayjs(natureConfig.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
