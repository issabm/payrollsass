import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModeInput, getModeInputIdentifier } from '../mode-input.model';

export type EntityResponseType = HttpResponse<IModeInput>;
export type EntityArrayResponseType = HttpResponse<IModeInput[]>;

@Injectable({ providedIn: 'root' })
export class ModeInputService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-inputs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(modeInput: IModeInput): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeInput);
    return this.http
      .post<IModeInput>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(modeInput: IModeInput): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeInput);
    return this.http
      .put<IModeInput>(`${this.resourceUrl}/${getModeInputIdentifier(modeInput) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(modeInput: IModeInput): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeInput);
    return this.http
      .patch<IModeInput>(`${this.resourceUrl}/${getModeInputIdentifier(modeInput) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModeInput>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModeInput[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addModeInputToCollectionIfMissing(
    modeInputCollection: IModeInput[],
    ...modeInputsToCheck: (IModeInput | null | undefined)[]
  ): IModeInput[] {
    const modeInputs: IModeInput[] = modeInputsToCheck.filter(isPresent);
    if (modeInputs.length > 0) {
      const modeInputCollectionIdentifiers = modeInputCollection.map(modeInputItem => getModeInputIdentifier(modeInputItem)!);
      const modeInputsToAdd = modeInputs.filter(modeInputItem => {
        const modeInputIdentifier = getModeInputIdentifier(modeInputItem);
        if (modeInputIdentifier == null || modeInputCollectionIdentifiers.includes(modeInputIdentifier)) {
          return false;
        }
        modeInputCollectionIdentifiers.push(modeInputIdentifier);
        return true;
      });
      return [...modeInputsToAdd, ...modeInputCollection];
    }
    return modeInputCollection;
  }

  protected convertDateFromClient(modeInput: IModeInput): IModeInput {
    return Object.assign({}, modeInput, {
      dateop: modeInput.dateop?.isValid() ? modeInput.dateop.toJSON() : undefined,
      createdDate: modeInput.createdDate?.isValid() ? modeInput.createdDate.toJSON() : undefined,
      modifiedDate: modeInput.modifiedDate?.isValid() ? modeInput.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((modeInput: IModeInput) => {
        modeInput.dateop = modeInput.dateop ? dayjs(modeInput.dateop) : undefined;
        modeInput.createdDate = modeInput.createdDate ? dayjs(modeInput.createdDate) : undefined;
        modeInput.modifiedDate = modeInput.modifiedDate ? dayjs(modeInput.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
