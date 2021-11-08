import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IManagementResourceFun, ManagementResourceFun } from '../management-resource-fun.model';

import { ManagementResourceFunService } from './management-resource-fun.service';

describe('ManagementResourceFun Service', () => {
  let service: ManagementResourceFunService;
  let httpMock: HttpTestingController;
  let elemDefault: IManagementResourceFun;
  let expectedResult: IManagementResourceFun | IManagementResourceFun[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ManagementResourceFunService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      libEn: 'AAAAAAA',
      profile: 'AAAAAAA',
      enableAdd: false,
      enableCnst: false,
      enableDel: false,
      enableEd: false,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      op: 'AAAAAAA',
      util: 'AAAAAAA',
      isDeleted: false,
      createdDate: currentDate,
      modifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ManagementResourceFun', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ManagementResourceFun()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ManagementResourceFun', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libEn: 'BBBBBB',
          profile: 'BBBBBB',
          enableAdd: true,
          enableCnst: true,
          enableDel: true,
          enableEd: true,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ManagementResourceFun', () => {
      const patchObject = Object.assign(
        {
          profile: 'BBBBBB',
          enableDel: true,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ManagementResourceFun()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ManagementResourceFun', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libEn: 'BBBBBB',
          profile: 'BBBBBB',
          enableAdd: true,
          enableCnst: true,
          enableDel: true,
          enableEd: true,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ManagementResourceFun', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addManagementResourceFunToCollectionIfMissing', () => {
      it('should add a ManagementResourceFun to an empty array', () => {
        const managementResourceFun: IManagementResourceFun = { id: 123 };
        expectedResult = service.addManagementResourceFunToCollectionIfMissing([], managementResourceFun);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResourceFun);
      });

      it('should not add a ManagementResourceFun to an array that contains it', () => {
        const managementResourceFun: IManagementResourceFun = { id: 123 };
        const managementResourceFunCollection: IManagementResourceFun[] = [
          {
            ...managementResourceFun,
          },
          { id: 456 },
        ];
        expectedResult = service.addManagementResourceFunToCollectionIfMissing(managementResourceFunCollection, managementResourceFun);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ManagementResourceFun to an array that doesn't contain it", () => {
        const managementResourceFun: IManagementResourceFun = { id: 123 };
        const managementResourceFunCollection: IManagementResourceFun[] = [{ id: 456 }];
        expectedResult = service.addManagementResourceFunToCollectionIfMissing(managementResourceFunCollection, managementResourceFun);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResourceFun);
      });

      it('should add only unique ManagementResourceFun to an array', () => {
        const managementResourceFunArray: IManagementResourceFun[] = [{ id: 123 }, { id: 456 }, { id: 68916 }];
        const managementResourceFunCollection: IManagementResourceFun[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceFunToCollectionIfMissing(
          managementResourceFunCollection,
          ...managementResourceFunArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const managementResourceFun: IManagementResourceFun = { id: 123 };
        const managementResourceFun2: IManagementResourceFun = { id: 456 };
        expectedResult = service.addManagementResourceFunToCollectionIfMissing([], managementResourceFun, managementResourceFun2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResourceFun);
        expect(expectedResult).toContain(managementResourceFun2);
      });

      it('should accept null and undefined values', () => {
        const managementResourceFun: IManagementResourceFun = { id: 123 };
        expectedResult = service.addManagementResourceFunToCollectionIfMissing([], null, managementResourceFun, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResourceFun);
      });

      it('should return initial array if no ManagementResourceFun is added', () => {
        const managementResourceFunCollection: IManagementResourceFun[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceFunToCollectionIfMissing(managementResourceFunCollection, undefined, null);
        expect(expectedResult).toEqual(managementResourceFunCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
