import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INiveauScolaire, NiveauScolaire } from '../niveau-scolaire.model';

import { NiveauScolaireService } from './niveau-scolaire.service';

describe('NiveauScolaire Service', () => {
  let service: NiveauScolaireService;
  let httpMock: HttpTestingController;
  let elemDefault: INiveauScolaire;
  let expectedResult: INiveauScolaire | INiveauScolaire[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NiveauScolaireService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      orderLevel: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
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

    it('should create a NiveauScolaire', () => {
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

      service.create(new NiveauScolaire()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NiveauScolaire', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderLevel: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
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

    it('should partial update a NiveauScolaire', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          util: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new NiveauScolaire()
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

    it('should return a list of NiveauScolaire', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderLevel: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
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

    it('should delete a NiveauScolaire', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNiveauScolaireToCollectionIfMissing', () => {
      it('should add a NiveauScolaire to an empty array', () => {
        const niveauScolaire: INiveauScolaire = { id: 123 };
        expectedResult = service.addNiveauScolaireToCollectionIfMissing([], niveauScolaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(niveauScolaire);
      });

      it('should not add a NiveauScolaire to an array that contains it', () => {
        const niveauScolaire: INiveauScolaire = { id: 123 };
        const niveauScolaireCollection: INiveauScolaire[] = [
          {
            ...niveauScolaire,
          },
          { id: 456 },
        ];
        expectedResult = service.addNiveauScolaireToCollectionIfMissing(niveauScolaireCollection, niveauScolaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NiveauScolaire to an array that doesn't contain it", () => {
        const niveauScolaire: INiveauScolaire = { id: 123 };
        const niveauScolaireCollection: INiveauScolaire[] = [{ id: 456 }];
        expectedResult = service.addNiveauScolaireToCollectionIfMissing(niveauScolaireCollection, niveauScolaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(niveauScolaire);
      });

      it('should add only unique NiveauScolaire to an array', () => {
        const niveauScolaireArray: INiveauScolaire[] = [{ id: 123 }, { id: 456 }, { id: 30546 }];
        const niveauScolaireCollection: INiveauScolaire[] = [{ id: 123 }];
        expectedResult = service.addNiveauScolaireToCollectionIfMissing(niveauScolaireCollection, ...niveauScolaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const niveauScolaire: INiveauScolaire = { id: 123 };
        const niveauScolaire2: INiveauScolaire = { id: 456 };
        expectedResult = service.addNiveauScolaireToCollectionIfMissing([], niveauScolaire, niveauScolaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(niveauScolaire);
        expect(expectedResult).toContain(niveauScolaire2);
      });

      it('should accept null and undefined values', () => {
        const niveauScolaire: INiveauScolaire = { id: 123 };
        expectedResult = service.addNiveauScolaireToCollectionIfMissing([], null, niveauScolaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(niveauScolaire);
      });

      it('should return initial array if no NiveauScolaire is added', () => {
        const niveauScolaireCollection: INiveauScolaire[] = [{ id: 123 }];
        expectedResult = service.addNiveauScolaireToCollectionIfMissing(niveauScolaireCollection, undefined, null);
        expect(expectedResult).toEqual(niveauScolaireCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
