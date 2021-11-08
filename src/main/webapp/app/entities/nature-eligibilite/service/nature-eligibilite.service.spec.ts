import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureEligibilite, NatureEligibilite } from '../nature-eligibilite.model';

import { NatureEligibiliteService } from './nature-eligibilite.service';

describe('NatureEligibilite Service', () => {
  let service: NatureEligibiliteService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureEligibilite;
  let expectedResult: INatureEligibilite | INatureEligibilite[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureEligibiliteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
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

    it('should create a NatureEligibilite', () => {
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

      service.create(new NatureEligibilite()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureEligibilite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should partial update a NatureEligibilite', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          libFr: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
        },
        new NatureEligibilite()
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

    it('should return a list of NatureEligibilite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
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

    it('should delete a NatureEligibilite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureEligibiliteToCollectionIfMissing', () => {
      it('should add a NatureEligibilite to an empty array', () => {
        const natureEligibilite: INatureEligibilite = { id: 123 };
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing([], natureEligibilite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureEligibilite);
      });

      it('should not add a NatureEligibilite to an array that contains it', () => {
        const natureEligibilite: INatureEligibilite = { id: 123 };
        const natureEligibiliteCollection: INatureEligibilite[] = [
          {
            ...natureEligibilite,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing(natureEligibiliteCollection, natureEligibilite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureEligibilite to an array that doesn't contain it", () => {
        const natureEligibilite: INatureEligibilite = { id: 123 };
        const natureEligibiliteCollection: INatureEligibilite[] = [{ id: 456 }];
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing(natureEligibiliteCollection, natureEligibilite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureEligibilite);
      });

      it('should add only unique NatureEligibilite to an array', () => {
        const natureEligibiliteArray: INatureEligibilite[] = [{ id: 123 }, { id: 456 }, { id: 3540 }];
        const natureEligibiliteCollection: INatureEligibilite[] = [{ id: 123 }];
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing(natureEligibiliteCollection, ...natureEligibiliteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureEligibilite: INatureEligibilite = { id: 123 };
        const natureEligibilite2: INatureEligibilite = { id: 456 };
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing([], natureEligibilite, natureEligibilite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureEligibilite);
        expect(expectedResult).toContain(natureEligibilite2);
      });

      it('should accept null and undefined values', () => {
        const natureEligibilite: INatureEligibilite = { id: 123 };
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing([], null, natureEligibilite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureEligibilite);
      });

      it('should return initial array if no NatureEligibilite is added', () => {
        const natureEligibiliteCollection: INatureEligibilite[] = [{ id: 123 }];
        expectedResult = service.addNatureEligibiliteToCollectionIfMissing(natureEligibiliteCollection, undefined, null);
        expect(expectedResult).toEqual(natureEligibiliteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
