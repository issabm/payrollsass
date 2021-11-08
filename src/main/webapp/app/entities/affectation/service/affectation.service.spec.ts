import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAffectation, Affectation } from '../affectation.model';

import { AffectationService } from './affectation.service';

describe('Affectation Service', () => {
  let service: AffectationService;
  let httpMock: HttpTestingController;
  let elemDefault: IAffectation;
  let expectedResult: IAffectation | IAffectation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AffectationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      matricule: 'AAAAAAA',
      dateDebut: currentDate,
      dateFin: currentDate,
      dateop: currentDate,
      util: 'AAAAAAA',
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
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
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

    it('should create a Affectation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Affectation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Affectation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          matricule: 'BBBBBB',
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          util: 'BBBBBB',
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
          dateDebut: currentDate,
          dateFin: currentDate,
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

    it('should partial update a Affectation', () => {
      const patchObject = Object.assign(
        {
          matricule: 'BBBBBB',
          dateDebut: currentDate.format(DATE_FORMAT),
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Affectation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
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

    it('should return a list of Affectation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          matricule: 'BBBBBB',
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          util: 'BBBBBB',
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
          dateDebut: currentDate,
          dateFin: currentDate,
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

    it('should delete a Affectation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAffectationToCollectionIfMissing', () => {
      it('should add a Affectation to an empty array', () => {
        const affectation: IAffectation = { id: 123 };
        expectedResult = service.addAffectationToCollectionIfMissing([], affectation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affectation);
      });

      it('should not add a Affectation to an array that contains it', () => {
        const affectation: IAffectation = { id: 123 };
        const affectationCollection: IAffectation[] = [
          {
            ...affectation,
          },
          { id: 456 },
        ];
        expectedResult = service.addAffectationToCollectionIfMissing(affectationCollection, affectation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Affectation to an array that doesn't contain it", () => {
        const affectation: IAffectation = { id: 123 };
        const affectationCollection: IAffectation[] = [{ id: 456 }];
        expectedResult = service.addAffectationToCollectionIfMissing(affectationCollection, affectation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affectation);
      });

      it('should add only unique Affectation to an array', () => {
        const affectationArray: IAffectation[] = [{ id: 123 }, { id: 456 }, { id: 16558 }];
        const affectationCollection: IAffectation[] = [{ id: 123 }];
        expectedResult = service.addAffectationToCollectionIfMissing(affectationCollection, ...affectationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const affectation: IAffectation = { id: 123 };
        const affectation2: IAffectation = { id: 456 };
        expectedResult = service.addAffectationToCollectionIfMissing([], affectation, affectation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affectation);
        expect(expectedResult).toContain(affectation2);
      });

      it('should accept null and undefined values', () => {
        const affectation: IAffectation = { id: 123 };
        expectedResult = service.addAffectationToCollectionIfMissing([], null, affectation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affectation);
      });

      it('should return initial array if no Affectation is added', () => {
        const affectationCollection: IAffectation[] = [{ id: 123 }];
        expectedResult = service.addAffectationToCollectionIfMissing(affectationCollection, undefined, null);
        expect(expectedResult).toEqual(affectationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
