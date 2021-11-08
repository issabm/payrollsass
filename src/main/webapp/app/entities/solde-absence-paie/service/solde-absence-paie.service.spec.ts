import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISoldeAbsencePaie, SoldeAbsencePaie } from '../solde-absence-paie.model';

import { SoldeAbsencePaieService } from './solde-absence-paie.service';

describe('SoldeAbsencePaie Service', () => {
  let service: SoldeAbsencePaieService;
  let httpMock: HttpTestingController;
  let elemDefault: ISoldeAbsencePaie;
  let expectedResult: ISoldeAbsencePaie | ISoldeAbsencePaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SoldeAbsencePaieService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      annee: 0,
      mois: 0,
      nbDays: 0,
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

    it('should create a SoldeAbsencePaie', () => {
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

      service.create(new SoldeAbsencePaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SoldeAbsencePaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          mois: 1,
          nbDays: 1,
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

    it('should partial update a SoldeAbsencePaie', () => {
      const patchObject = Object.assign(
        {
          annee: 1,
          mois: 1,
          nbDays: 1,
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SoldeAbsencePaie()
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

    it('should return a list of SoldeAbsencePaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          mois: 1,
          nbDays: 1,
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

    it('should delete a SoldeAbsencePaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSoldeAbsencePaieToCollectionIfMissing', () => {
      it('should add a SoldeAbsencePaie to an empty array', () => {
        const soldeAbsencePaie: ISoldeAbsencePaie = { id: 123 };
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing([], soldeAbsencePaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsencePaie);
      });

      it('should not add a SoldeAbsencePaie to an array that contains it', () => {
        const soldeAbsencePaie: ISoldeAbsencePaie = { id: 123 };
        const soldeAbsencePaieCollection: ISoldeAbsencePaie[] = [
          {
            ...soldeAbsencePaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing(soldeAbsencePaieCollection, soldeAbsencePaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SoldeAbsencePaie to an array that doesn't contain it", () => {
        const soldeAbsencePaie: ISoldeAbsencePaie = { id: 123 };
        const soldeAbsencePaieCollection: ISoldeAbsencePaie[] = [{ id: 456 }];
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing(soldeAbsencePaieCollection, soldeAbsencePaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsencePaie);
      });

      it('should add only unique SoldeAbsencePaie to an array', () => {
        const soldeAbsencePaieArray: ISoldeAbsencePaie[] = [{ id: 123 }, { id: 456 }, { id: 77737 }];
        const soldeAbsencePaieCollection: ISoldeAbsencePaie[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing(soldeAbsencePaieCollection, ...soldeAbsencePaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const soldeAbsencePaie: ISoldeAbsencePaie = { id: 123 };
        const soldeAbsencePaie2: ISoldeAbsencePaie = { id: 456 };
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing([], soldeAbsencePaie, soldeAbsencePaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsencePaie);
        expect(expectedResult).toContain(soldeAbsencePaie2);
      });

      it('should accept null and undefined values', () => {
        const soldeAbsencePaie: ISoldeAbsencePaie = { id: 123 };
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing([], null, soldeAbsencePaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsencePaie);
      });

      it('should return initial array if no SoldeAbsencePaie is added', () => {
        const soldeAbsencePaieCollection: ISoldeAbsencePaie[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsencePaieToCollectionIfMissing(soldeAbsencePaieCollection, undefined, null);
        expect(expectedResult).toEqual(soldeAbsencePaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
