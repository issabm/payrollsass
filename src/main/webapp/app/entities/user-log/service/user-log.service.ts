import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserLog, getUserLogIdentifier } from '../user-log.model';

export type EntityResponseType = HttpResponse<IUserLog>;
export type EntityArrayResponseType = HttpResponse<IUserLog[]>;

@Injectable({ providedIn: 'root' })
export class UserLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userLog: IUserLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLog);
    return this.http
      .post<IUserLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userLog: IUserLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLog);
    return this.http
      .put<IUserLog>(`${this.resourceUrl}/${getUserLogIdentifier(userLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userLog: IUserLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLog);
    return this.http
      .patch<IUserLog>(`${this.resourceUrl}/${getUserLogIdentifier(userLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserLogToCollectionIfMissing(userLogCollection: IUserLog[], ...userLogsToCheck: (IUserLog | null | undefined)[]): IUserLog[] {
    const userLogs: IUserLog[] = userLogsToCheck.filter(isPresent);
    if (userLogs.length > 0) {
      const userLogCollectionIdentifiers = userLogCollection.map(userLogItem => getUserLogIdentifier(userLogItem)!);
      const userLogsToAdd = userLogs.filter(userLogItem => {
        const userLogIdentifier = getUserLogIdentifier(userLogItem);
        if (userLogIdentifier == null || userLogCollectionIdentifiers.includes(userLogIdentifier)) {
          return false;
        }
        userLogCollectionIdentifiers.push(userLogIdentifier);
        return true;
      });
      return [...userLogsToAdd, ...userLogCollection];
    }
    return userLogCollection;
  }

  protected convertDateFromClient(userLog: IUserLog): IUserLog {
    return Object.assign({}, userLog, {
      dateOp: userLog.dateOp?.isValid() ? userLog.dateOp.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOp = res.body.dateOp ? dayjs(res.body.dateOp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userLog: IUserLog) => {
        userLog.dateOp = userLog.dateOp ? dayjs(userLog.dateOp) : undefined;
      });
    }
    return res;
  }
}
