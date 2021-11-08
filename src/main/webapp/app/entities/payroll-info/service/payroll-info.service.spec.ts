import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPayrollInfo, PayrollInfo } from '../payroll-info.model';

import { PayrollInfoService } from './payroll-info.service';

describe('PayrollInfo Service', () => {
  let service: PayrollInfoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPayrollInfo;
  let expectedResult: IPayrollInfo | IPayrollInfo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PayrollInfoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      util: 'AAAAAAA',
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
      libEr: 'AAAAAAA',
      valueRebrique: 0,
      valueRebDevise: 0,
      tauxChange: 0,
      dateCalcul: currentDate,
      dateEffect: currentDate,
      calculBy: 'AAAAAAA',
      effectBy: 'AAAAAAA',
      dateSituation: currentDate,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      op: 'AAAAAAA',
      isDeleted: false,
      createdDate: currentDate,
      modifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateEffect: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
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

    it('should create a PayrollInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateEffect: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateEffect: currentDate,
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new PayrollInfo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PayrollInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          libEr: 'BBBBBB',
          valueRebrique: 1,
          valueRebDevise: 1,
          tauxChange: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateEffect: currentDate.format(DATE_FORMAT),
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateEffect: currentDate,
          dateSituation: currentDate,
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

    it('should partial update a PayrollInfo', () => {
      const patchObject = Object.assign(
        {
          libFr: 'BBBBBB',
          libEr: 'BBBBBB',
          valueRebrique: 1,
          tauxChange: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
        },
        new PayrollInfo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateEffect: currentDate,
          dateSituation: currentDate,
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

    it('should return a list of PayrollInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          libEr: 'BBBBBB',
          valueRebrique: 1,
          valueRebDevise: 1,
          tauxChange: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateEffect: currentDate.format(DATE_FORMAT),
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateEffect: currentDate,
          dateSituation: currentDate,
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

    it('should delete a PayrollInfo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPayrollInfoToCollectionIfMissing', () => {
      it('should add a PayrollInfo to an empty array', () => {
        const payrollInfo: IPayrollInfo = { id: 123 };
        expectedResult = service.addPayrollInfoToCollectionIfMissing([], payrollInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payrollInfo);
      });

      it('should not add a PayrollInfo to an array that contains it', () => {
        const payrollInfo: IPayrollInfo = { id: 123 };
        const payrollInfoCollection: IPayrollInfo[] = [
          {
            ...payrollInfo,
          },
          { id: 456 },
        ];
        expectedResult = service.addPayrollInfoToCollectionIfMissing(payrollInfoCollection, payrollInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PayrollInfo to an array that doesn't contain it", () => {
        const payrollInfo: IPayrollInfo = { id: 123 };
        const payrollInfoCollection: IPayrollInfo[] = [{ id: 456 }];
        expectedResult = service.addPayrollInfoToCollectionIfMissing(payrollInfoCollection, payrollInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payrollInfo);
      });

      it('should add only unique PayrollInfo to an array', () => {
        const payrollInfoArray: IPayrollInfo[] = [{ id: 123 }, { id: 456 }, { id: 93715 }];
        const payrollInfoCollection: IPayrollInfo[] = [{ id: 123 }];
        expectedResult = service.addPayrollInfoToCollectionIfMissing(payrollInfoCollection, ...payrollInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const payrollInfo: IPayrollInfo = { id: 123 };
        const payrollInfo2: IPayrollInfo = { id: 456 };
        expectedResult = service.addPayrollInfoToCollectionIfMissing([], payrollInfo, payrollInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payrollInfo);
        expect(expectedResult).toContain(payrollInfo2);
      });

      it('should accept null and undefined values', () => {
        const payrollInfo: IPayrollInfo = { id: 123 };
        expectedResult = service.addPayrollInfoToCollectionIfMissing([], null, payrollInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payrollInfo);
      });

      it('should return initial array if no PayrollInfo is added', () => {
        const payrollInfoCollection: IPayrollInfo[] = [{ id: 123 }];
        expectedResult = service.addPayrollInfoToCollectionIfMissing(payrollInfoCollection, undefined, null);
        expect(expectedResult).toEqual(payrollInfoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
