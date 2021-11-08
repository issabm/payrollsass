import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUserLog, UserLog } from '../user-log.model';

import { UserLogService } from './user-log.service';

describe('UserLog Service', () => {
  let service: UserLogService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserLog;
  let expectedResult: IUserLog | IUserLog[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserLogService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      op: 'AAAAAAA',
      util: 'AAAAAAA',
      dateOp: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UserLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOp: currentDate,
        },
        returnedFromService
      );

      service.create(new UserLog()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          op: 'BBBBBB',
          util: 'BBBBBB',
          dateOp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOp: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserLog', () => {
      const patchObject = Object.assign(
        {
          op: 'BBBBBB',
          util: 'BBBBBB',
        },
        new UserLog()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOp: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          op: 'BBBBBB',
          util: 'BBBBBB',
          dateOp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOp: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a UserLog', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserLogToCollectionIfMissing', () => {
      it('should add a UserLog to an empty array', () => {
        const userLog: IUserLog = { id: 123 };
        expectedResult = service.addUserLogToCollectionIfMissing([], userLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userLog);
      });

      it('should not add a UserLog to an array that contains it', () => {
        const userLog: IUserLog = { id: 123 };
        const userLogCollection: IUserLog[] = [
          {
            ...userLog,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserLogToCollectionIfMissing(userLogCollection, userLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserLog to an array that doesn't contain it", () => {
        const userLog: IUserLog = { id: 123 };
        const userLogCollection: IUserLog[] = [{ id: 456 }];
        expectedResult = service.addUserLogToCollectionIfMissing(userLogCollection, userLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userLog);
      });

      it('should add only unique UserLog to an array', () => {
        const userLogArray: IUserLog[] = [{ id: 123 }, { id: 456 }, { id: 24454 }];
        const userLogCollection: IUserLog[] = [{ id: 123 }];
        expectedResult = service.addUserLogToCollectionIfMissing(userLogCollection, ...userLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userLog: IUserLog = { id: 123 };
        const userLog2: IUserLog = { id: 456 };
        expectedResult = service.addUserLogToCollectionIfMissing([], userLog, userLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userLog);
        expect(expectedResult).toContain(userLog2);
      });

      it('should accept null and undefined values', () => {
        const userLog: IUserLog = { id: 123 };
        expectedResult = service.addUserLogToCollectionIfMissing([], null, userLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userLog);
      });

      it('should return initial array if no UserLog is added', () => {
        const userLogCollection: IUserLog[] = [{ id: 123 }];
        expectedResult = service.addUserLogToCollectionIfMissing(userLogCollection, undefined, null);
        expect(expectedResult).toEqual(userLogCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
