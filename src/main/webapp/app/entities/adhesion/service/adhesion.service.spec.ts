import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAdhesion, Adhesion } from '../adhesion.model';

import { AdhesionService } from './adhesion.service';

describe('Adhesion Service', () => {
  let service: AdhesionService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdhesion;
  let expectedResult: IAdhesion | IAdhesion[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdhesionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateDebut: currentDate,
      dateFin: currentDate,
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

    it('should create a Adhesion', () => {
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

      service.create(new Adhesion()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Adhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
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

    it('should partial update a Adhesion', () => {
      const patchObject = Object.assign(
        {
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Adhesion()
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

    it('should return a list of Adhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
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

    it('should delete a Adhesion', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdhesionToCollectionIfMissing', () => {
      it('should add a Adhesion to an empty array', () => {
        const adhesion: IAdhesion = { id: 123 };
        expectedResult = service.addAdhesionToCollectionIfMissing([], adhesion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adhesion);
      });

      it('should not add a Adhesion to an array that contains it', () => {
        const adhesion: IAdhesion = { id: 123 };
        const adhesionCollection: IAdhesion[] = [
          {
            ...adhesion,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdhesionToCollectionIfMissing(adhesionCollection, adhesion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Adhesion to an array that doesn't contain it", () => {
        const adhesion: IAdhesion = { id: 123 };
        const adhesionCollection: IAdhesion[] = [{ id: 456 }];
        expectedResult = service.addAdhesionToCollectionIfMissing(adhesionCollection, adhesion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adhesion);
      });

      it('should add only unique Adhesion to an array', () => {
        const adhesionArray: IAdhesion[] = [{ id: 123 }, { id: 456 }, { id: 9777 }];
        const adhesionCollection: IAdhesion[] = [{ id: 123 }];
        expectedResult = service.addAdhesionToCollectionIfMissing(adhesionCollection, ...adhesionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adhesion: IAdhesion = { id: 123 };
        const adhesion2: IAdhesion = { id: 456 };
        expectedResult = service.addAdhesionToCollectionIfMissing([], adhesion, adhesion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adhesion);
        expect(expectedResult).toContain(adhesion2);
      });

      it('should accept null and undefined values', () => {
        const adhesion: IAdhesion = { id: 123 };
        expectedResult = service.addAdhesionToCollectionIfMissing([], null, adhesion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adhesion);
      });

      it('should return initial array if no Adhesion is added', () => {
        const adhesionCollection: IAdhesion[] = [{ id: 123 }];
        expectedResult = service.addAdhesionToCollectionIfMissing(adhesionCollection, undefined, null);
        expect(expectedResult).toEqual(adhesionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
