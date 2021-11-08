import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEligibiliteExclude, EligibiliteExclude } from '../eligibilite-exclude.model';

import { EligibiliteExcludeService } from './eligibilite-exclude.service';

describe('EligibiliteExclude Service', () => {
  let service: EligibiliteExcludeService;
  let httpMock: HttpTestingController;
  let elemDefault: IEligibiliteExclude;
  let expectedResult: IEligibiliteExclude | IEligibiliteExclude[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EligibiliteExcludeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      anneeBegin: 0,
      moisBegin: 0,
      anneeEnd: 0,
      moisEnd: 0,
      matricule: 'AAAAAAA',
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
      annee: 0,
      valPayroll: 0,
      nbDaysLeave: 0,
      pourValPayroll: 0,
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

    it('should create a EligibiliteExclude', () => {
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

      service.create(new EligibiliteExclude()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EligibiliteExclude', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anneeBegin: 1,
          moisBegin: 1,
          anneeEnd: 1,
          moisEnd: 1,
          matricule: 'BBBBBB',
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          annee: 1,
          valPayroll: 1,
          nbDaysLeave: 1,
          pourValPayroll: 1,
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

    it('should partial update a EligibiliteExclude', () => {
      const patchObject = Object.assign(
        {
          moisBegin: 1,
          anneeEnd: 1,
          moisEnd: 1,
          matricule: 'BBBBBB',
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          pourValPayroll: 1,
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new EligibiliteExclude()
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

    it('should return a list of EligibiliteExclude', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anneeBegin: 1,
          moisBegin: 1,
          anneeEnd: 1,
          moisEnd: 1,
          matricule: 'BBBBBB',
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          annee: 1,
          valPayroll: 1,
          nbDaysLeave: 1,
          pourValPayroll: 1,
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

    it('should delete a EligibiliteExclude', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEligibiliteExcludeToCollectionIfMissing', () => {
      it('should add a EligibiliteExclude to an empty array', () => {
        const eligibiliteExclude: IEligibiliteExclude = { id: 123 };
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing([], eligibiliteExclude);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eligibiliteExclude);
      });

      it('should not add a EligibiliteExclude to an array that contains it', () => {
        const eligibiliteExclude: IEligibiliteExclude = { id: 123 };
        const eligibiliteExcludeCollection: IEligibiliteExclude[] = [
          {
            ...eligibiliteExclude,
          },
          { id: 456 },
        ];
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing(eligibiliteExcludeCollection, eligibiliteExclude);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EligibiliteExclude to an array that doesn't contain it", () => {
        const eligibiliteExclude: IEligibiliteExclude = { id: 123 };
        const eligibiliteExcludeCollection: IEligibiliteExclude[] = [{ id: 456 }];
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing(eligibiliteExcludeCollection, eligibiliteExclude);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eligibiliteExclude);
      });

      it('should add only unique EligibiliteExclude to an array', () => {
        const eligibiliteExcludeArray: IEligibiliteExclude[] = [{ id: 123 }, { id: 456 }, { id: 61315 }];
        const eligibiliteExcludeCollection: IEligibiliteExclude[] = [{ id: 123 }];
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing(eligibiliteExcludeCollection, ...eligibiliteExcludeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eligibiliteExclude: IEligibiliteExclude = { id: 123 };
        const eligibiliteExclude2: IEligibiliteExclude = { id: 456 };
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing([], eligibiliteExclude, eligibiliteExclude2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eligibiliteExclude);
        expect(expectedResult).toContain(eligibiliteExclude2);
      });

      it('should accept null and undefined values', () => {
        const eligibiliteExclude: IEligibiliteExclude = { id: 123 };
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing([], null, eligibiliteExclude, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eligibiliteExclude);
      });

      it('should return initial array if no EligibiliteExclude is added', () => {
        const eligibiliteExcludeCollection: IEligibiliteExclude[] = [{ id: 123 }];
        expectedResult = service.addEligibiliteExcludeToCollectionIfMissing(eligibiliteExcludeCollection, undefined, null);
        expect(expectedResult).toEqual(eligibiliteExcludeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
