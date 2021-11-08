import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureAbsence, NatureAbsence } from '../nature-absence.model';

import { NatureAbsenceService } from './nature-absence.service';

describe('NatureAbsence Service', () => {
  let service: NatureAbsenceService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureAbsence;
  let expectedResult: INatureAbsence | INatureAbsence[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureAbsenceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      util: 'AAAAAAA',
      dateop: currentDate,
      nbDays: 0,
      valuePaied: 0,
      modifiedBy: 'AAAAAAA',
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

    it('should create a NatureAbsence', () => {
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

      service.create(new NatureAbsence()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureAbsence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          nbDays: 1,
          valuePaied: 1,
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
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

    it('should partial update a NatureAbsence', () => {
      const patchObject = Object.assign(
        {
          nbDays: 1,
          valuePaied: 1,
          modifiedBy: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new NatureAbsence()
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

    it('should return a list of NatureAbsence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          nbDays: 1,
          valuePaied: 1,
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
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

    it('should delete a NatureAbsence', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureAbsenceToCollectionIfMissing', () => {
      it('should add a NatureAbsence to an empty array', () => {
        const natureAbsence: INatureAbsence = { id: 123 };
        expectedResult = service.addNatureAbsenceToCollectionIfMissing([], natureAbsence);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAbsence);
      });

      it('should not add a NatureAbsence to an array that contains it', () => {
        const natureAbsence: INatureAbsence = { id: 123 };
        const natureAbsenceCollection: INatureAbsence[] = [
          {
            ...natureAbsence,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureAbsenceToCollectionIfMissing(natureAbsenceCollection, natureAbsence);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureAbsence to an array that doesn't contain it", () => {
        const natureAbsence: INatureAbsence = { id: 123 };
        const natureAbsenceCollection: INatureAbsence[] = [{ id: 456 }];
        expectedResult = service.addNatureAbsenceToCollectionIfMissing(natureAbsenceCollection, natureAbsence);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAbsence);
      });

      it('should add only unique NatureAbsence to an array', () => {
        const natureAbsenceArray: INatureAbsence[] = [{ id: 123 }, { id: 456 }, { id: 84494 }];
        const natureAbsenceCollection: INatureAbsence[] = [{ id: 123 }];
        expectedResult = service.addNatureAbsenceToCollectionIfMissing(natureAbsenceCollection, ...natureAbsenceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureAbsence: INatureAbsence = { id: 123 };
        const natureAbsence2: INatureAbsence = { id: 456 };
        expectedResult = service.addNatureAbsenceToCollectionIfMissing([], natureAbsence, natureAbsence2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAbsence);
        expect(expectedResult).toContain(natureAbsence2);
      });

      it('should accept null and undefined values', () => {
        const natureAbsence: INatureAbsence = { id: 123 };
        expectedResult = service.addNatureAbsenceToCollectionIfMissing([], null, natureAbsence, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAbsence);
      });

      it('should return initial array if no NatureAbsence is added', () => {
        const natureAbsenceCollection: INatureAbsence[] = [{ id: 123 }];
        expectedResult = service.addNatureAbsenceToCollectionIfMissing(natureAbsenceCollection, undefined, null);
        expect(expectedResult).toEqual(natureAbsenceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
