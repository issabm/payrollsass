import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISoldeAbsence, SoldeAbsence } from '../solde-absence.model';

import { SoldeAbsenceService } from './solde-absence.service';

describe('SoldeAbsence Service', () => {
  let service: SoldeAbsenceService;
  let httpMock: HttpTestingController;
  let elemDefault: ISoldeAbsence;
  let expectedResult: ISoldeAbsence | ISoldeAbsence[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SoldeAbsenceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      annee: 0,
      nbDaysRight: 0,
      nbDaysConsumed: 0,
      nbDaysUnavailble: 0,
      nbDaysAvailble: 0,
      nbDaysLeft: 0,
      util: 'AAAAAAA',
      dateop: currentDate,
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

    it('should create a SoldeAbsence', () => {
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

      service.create(new SoldeAbsence()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SoldeAbsence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          nbDaysRight: 1,
          nbDaysConsumed: 1,
          nbDaysUnavailble: 1,
          nbDaysAvailble: 1,
          nbDaysLeft: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
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

    it('should partial update a SoldeAbsence', () => {
      const patchObject = Object.assign(
        {
          nbDaysConsumed: 1,
          nbDaysAvailble: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SoldeAbsence()
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

    it('should return a list of SoldeAbsence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          nbDaysRight: 1,
          nbDaysConsumed: 1,
          nbDaysUnavailble: 1,
          nbDaysAvailble: 1,
          nbDaysLeft: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
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

    it('should delete a SoldeAbsence', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSoldeAbsenceToCollectionIfMissing', () => {
      it('should add a SoldeAbsence to an empty array', () => {
        const soldeAbsence: ISoldeAbsence = { id: 123 };
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing([], soldeAbsence);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsence);
      });

      it('should not add a SoldeAbsence to an array that contains it', () => {
        const soldeAbsence: ISoldeAbsence = { id: 123 };
        const soldeAbsenceCollection: ISoldeAbsence[] = [
          {
            ...soldeAbsence,
          },
          { id: 456 },
        ];
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing(soldeAbsenceCollection, soldeAbsence);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SoldeAbsence to an array that doesn't contain it", () => {
        const soldeAbsence: ISoldeAbsence = { id: 123 };
        const soldeAbsenceCollection: ISoldeAbsence[] = [{ id: 456 }];
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing(soldeAbsenceCollection, soldeAbsence);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsence);
      });

      it('should add only unique SoldeAbsence to an array', () => {
        const soldeAbsenceArray: ISoldeAbsence[] = [{ id: 123 }, { id: 456 }, { id: 6818 }];
        const soldeAbsenceCollection: ISoldeAbsence[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing(soldeAbsenceCollection, ...soldeAbsenceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const soldeAbsence: ISoldeAbsence = { id: 123 };
        const soldeAbsence2: ISoldeAbsence = { id: 456 };
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing([], soldeAbsence, soldeAbsence2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsence);
        expect(expectedResult).toContain(soldeAbsence2);
      });

      it('should accept null and undefined values', () => {
        const soldeAbsence: ISoldeAbsence = { id: 123 };
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing([], null, soldeAbsence, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsence);
      });

      it('should return initial array if no SoldeAbsence is added', () => {
        const soldeAbsenceCollection: ISoldeAbsence[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsenceToCollectionIfMissing(soldeAbsenceCollection, undefined, null);
        expect(expectedResult).toEqual(soldeAbsenceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
