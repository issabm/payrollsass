import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureAdhesion, NatureAdhesion } from '../nature-adhesion.model';

import { NatureAdhesionService } from './nature-adhesion.service';

describe('NatureAdhesion Service', () => {
  let service: NatureAdhesionService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureAdhesion;
  let expectedResult: INatureAdhesion | INatureAdhesion[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureAdhesionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
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

    it('should create a NatureAdhesion', () => {
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

      service.create(new NatureAdhesion()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureAdhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a NatureAdhesion', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          isDeleted: true,
        },
        new NatureAdhesion()
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

    it('should return a list of NatureAdhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a NatureAdhesion', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureAdhesionToCollectionIfMissing', () => {
      it('should add a NatureAdhesion to an empty array', () => {
        const natureAdhesion: INatureAdhesion = { id: 123 };
        expectedResult = service.addNatureAdhesionToCollectionIfMissing([], natureAdhesion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAdhesion);
      });

      it('should not add a NatureAdhesion to an array that contains it', () => {
        const natureAdhesion: INatureAdhesion = { id: 123 };
        const natureAdhesionCollection: INatureAdhesion[] = [
          {
            ...natureAdhesion,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureAdhesionToCollectionIfMissing(natureAdhesionCollection, natureAdhesion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureAdhesion to an array that doesn't contain it", () => {
        const natureAdhesion: INatureAdhesion = { id: 123 };
        const natureAdhesionCollection: INatureAdhesion[] = [{ id: 456 }];
        expectedResult = service.addNatureAdhesionToCollectionIfMissing(natureAdhesionCollection, natureAdhesion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAdhesion);
      });

      it('should add only unique NatureAdhesion to an array', () => {
        const natureAdhesionArray: INatureAdhesion[] = [{ id: 123 }, { id: 456 }, { id: 68300 }];
        const natureAdhesionCollection: INatureAdhesion[] = [{ id: 123 }];
        expectedResult = service.addNatureAdhesionToCollectionIfMissing(natureAdhesionCollection, ...natureAdhesionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureAdhesion: INatureAdhesion = { id: 123 };
        const natureAdhesion2: INatureAdhesion = { id: 456 };
        expectedResult = service.addNatureAdhesionToCollectionIfMissing([], natureAdhesion, natureAdhesion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAdhesion);
        expect(expectedResult).toContain(natureAdhesion2);
      });

      it('should accept null and undefined values', () => {
        const natureAdhesion: INatureAdhesion = { id: 123 };
        expectedResult = service.addNatureAdhesionToCollectionIfMissing([], null, natureAdhesion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAdhesion);
      });

      it('should return initial array if no NatureAdhesion is added', () => {
        const natureAdhesionCollection: INatureAdhesion[] = [{ id: 123 }];
        expectedResult = service.addNatureAdhesionToCollectionIfMissing(natureAdhesionCollection, undefined, null);
        expect(expectedResult).toEqual(natureAdhesionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
