import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEligibilite, Eligibilite } from '../eligibilite.model';

import { EligibiliteService } from './eligibilite.service';

describe('Eligibilite Service', () => {
  let service: EligibiliteService;
  let httpMock: HttpTestingController;
  let elemDefault: IEligibilite;
  let expectedResult: IEligibilite | IEligibilite[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EligibiliteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
      annee: 0,
      mois: 0,
      nbEnt: 0,
      ageEnt: 0,
      matricule: 'AAAAAAA',
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
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

    it('should create a Eligibilite', () => {
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

      service.create(new Eligibilite()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Eligibilite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          annee: 1,
          mois: 1,
          nbEnt: 1,
          ageEnt: 1,
          matricule: 'BBBBBB',
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should partial update a Eligibilite', () => {
      const patchObject = Object.assign(
        {
          mois: 1,
          libAr: 'BBBBBB',
          valPayroll: 1,
          nbDaysLeave: 1,
          pourValPayroll: 1,
          op: 'BBBBBB',
          util: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Eligibilite()
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

    it('should return a list of Eligibilite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          annee: 1,
          mois: 1,
          nbEnt: 1,
          ageEnt: 1,
          matricule: 'BBBBBB',
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should delete a Eligibilite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEligibiliteToCollectionIfMissing', () => {
      it('should add a Eligibilite to an empty array', () => {
        const eligibilite: IEligibilite = { id: 123 };
        expectedResult = service.addEligibiliteToCollectionIfMissing([], eligibilite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eligibilite);
      });

      it('should not add a Eligibilite to an array that contains it', () => {
        const eligibilite: IEligibilite = { id: 123 };
        const eligibiliteCollection: IEligibilite[] = [
          {
            ...eligibilite,
          },
          { id: 456 },
        ];
        expectedResult = service.addEligibiliteToCollectionIfMissing(eligibiliteCollection, eligibilite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Eligibilite to an array that doesn't contain it", () => {
        const eligibilite: IEligibilite = { id: 123 };
        const eligibiliteCollection: IEligibilite[] = [{ id: 456 }];
        expectedResult = service.addEligibiliteToCollectionIfMissing(eligibiliteCollection, eligibilite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eligibilite);
      });

      it('should add only unique Eligibilite to an array', () => {
        const eligibiliteArray: IEligibilite[] = [{ id: 123 }, { id: 456 }, { id: 25577 }];
        const eligibiliteCollection: IEligibilite[] = [{ id: 123 }];
        expectedResult = service.addEligibiliteToCollectionIfMissing(eligibiliteCollection, ...eligibiliteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eligibilite: IEligibilite = { id: 123 };
        const eligibilite2: IEligibilite = { id: 456 };
        expectedResult = service.addEligibiliteToCollectionIfMissing([], eligibilite, eligibilite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eligibilite);
        expect(expectedResult).toContain(eligibilite2);
      });

      it('should accept null and undefined values', () => {
        const eligibilite: IEligibilite = { id: 123 };
        expectedResult = service.addEligibiliteToCollectionIfMissing([], null, eligibilite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eligibilite);
      });

      it('should return initial array if no Eligibilite is added', () => {
        const eligibiliteCollection: IEligibilite[] = [{ id: 123 }];
        expectedResult = service.addEligibiliteToCollectionIfMissing(eligibiliteCollection, undefined, null);
        expect(expectedResult).toEqual(eligibiliteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
