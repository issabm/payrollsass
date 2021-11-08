import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPalierPlate, getPalierPlateIdentifier } from '../palier-plate.model';

export type EntityResponseType = HttpResponse<IPalierPlate>;
export type EntityArrayResponseType = HttpResponse<IPalierPlate[]>;

@Injectable({ providedIn: 'root' })
export class PalierPlateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/palier-plates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(palierPlate: IPalierPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierPlate);
    return this.http
      .post<IPalierPlate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(palierPlate: IPalierPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierPlate);
    return this.http
      .put<IPalierPlate>(`${this.resourceUrl}/${getPalierPlateIdentifier(palierPlate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(palierPlate: IPalierPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierPlate);
    return this.http
      .patch<IPalierPlate>(`${this.resourceUrl}/${getPalierPlateIdentifier(palierPlate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPalierPlate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPalierPlate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPalierPlateToCollectionIfMissing(
    palierPlateCollection: IPalierPlate[],
    ...palierPlatesToCheck: (IPalierPlate | null | undefined)[]
  ): IPalierPlate[] {
    const palierPlates: IPalierPlate[] = palierPlatesToCheck.filter(isPresent);
    if (palierPlates.length > 0) {
      const palierPlateCollectionIdentifiers = palierPlateCollection.map(palierPlateItem => getPalierPlateIdentifier(palierPlateItem)!);
      const palierPlatesToAdd = palierPlates.filter(palierPlateItem => {
        const palierPlateIdentifier = getPalierPlateIdentifier(palierPlateItem);
        if (palierPlateIdentifier == null || palierPlateCollectionIdentifiers.includes(palierPlateIdentifier)) {
          return false;
        }
        palierPlateCollectionIdentifiers.push(palierPlateIdentifier);
        return true;
      });
      return [...palierPlatesToAdd, ...palierPlateCollection];
    }
    return palierPlateCollection;
  }

  protected convertDateFromClient(palierPlate: IPalierPlate): IPalierPlate {
    return Object.assign({}, palierPlate, {
      dateop: palierPlate.dateop?.isValid() ? palierPlate.dateop.toJSON() : undefined,
      dateModif: palierPlate.dateModif?.isValid() ? palierPlate.dateModif.toJSON() : undefined,
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
      res.body.forEach((palierPlate: IPalierPlate) => {
        palierPlate.dateop = palierPlate.dateop ? dayjs(palierPlate.dateop) : undefined;
        palierPlate.dateModif = palierPlate.dateModif ? dayjs(palierPlate.dateModif) : undefined;
      });
    }
    return res;
  }
}
