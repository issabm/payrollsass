import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEntityAdhesion, EntityAdhesion } from '../entity-adhesion.model';

import { EntityAdhesionService } from './entity-adhesion.service';

describe('EntityAdhesion Service', () => {
  let service: EntityAdhesionService;
  let httpMock: HttpTestingController;
  let elemDefault: IEntityAdhesion;
  let expectedResult: IEntityAdhesion | IEntityAdhesion[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EntityAdhesionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
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

    it('should create a EntityAdhesion', () => {
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

      service.create(new EntityAdhesion()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EntityAdhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should partial update a EntityAdhesion', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libFr: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new EntityAdhesion()
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

    it('should return a list of EntityAdhesion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should delete a EntityAdhesion', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEntityAdhesionToCollectionIfMissing', () => {
      it('should add a EntityAdhesion to an empty array', () => {
        const entityAdhesion: IEntityAdhesion = { id: 123 };
        expectedResult = service.addEntityAdhesionToCollectionIfMissing([], entityAdhesion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entityAdhesion);
      });

      it('should not add a EntityAdhesion to an array that contains it', () => {
        const entityAdhesion: IEntityAdhesion = { id: 123 };
        const entityAdhesionCollection: IEntityAdhesion[] = [
          {
            ...entityAdhesion,
          },
          { id: 456 },
        ];
        expectedResult = service.addEntityAdhesionToCollectionIfMissing(entityAdhesionCollection, entityAdhesion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EntityAdhesion to an array that doesn't contain it", () => {
        const entityAdhesion: IEntityAdhesion = { id: 123 };
        const entityAdhesionCollection: IEntityAdhesion[] = [{ id: 456 }];
        expectedResult = service.addEntityAdhesionToCollectionIfMissing(entityAdhesionCollection, entityAdhesion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entityAdhesion);
      });

      it('should add only unique EntityAdhesion to an array', () => {
        const entityAdhesionArray: IEntityAdhesion[] = [{ id: 123 }, { id: 456 }, { id: 59448 }];
        const entityAdhesionCollection: IEntityAdhesion[] = [{ id: 123 }];
        expectedResult = service.addEntityAdhesionToCollectionIfMissing(entityAdhesionCollection, ...entityAdhesionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entityAdhesion: IEntityAdhesion = { id: 123 };
        const entityAdhesion2: IEntityAdhesion = { id: 456 };
        expectedResult = service.addEntityAdhesionToCollectionIfMissing([], entityAdhesion, entityAdhesion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entityAdhesion);
        expect(expectedResult).toContain(entityAdhesion2);
      });

      it('should accept null and undefined values', () => {
        const entityAdhesion: IEntityAdhesion = { id: 123 };
        expectedResult = service.addEntityAdhesionToCollectionIfMissing([], null, entityAdhesion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entityAdhesion);
      });

      it('should return initial array if no EntityAdhesion is added', () => {
        const entityAdhesionCollection: IEntityAdhesion[] = [{ id: 123 }];
        expectedResult = service.addEntityAdhesionToCollectionIfMissing(entityAdhesionCollection, undefined, null);
        expect(expectedResult).toEqual(entityAdhesionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
