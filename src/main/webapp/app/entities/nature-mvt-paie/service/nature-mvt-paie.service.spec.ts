import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureMvtPaie, NatureMvtPaie } from '../nature-mvt-paie.model';

import { NatureMvtPaieService } from './nature-mvt-paie.service';

describe('NatureMvtPaie Service', () => {
  let service: NatureMvtPaieService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureMvtPaie;
  let expectedResult: INatureMvtPaie | INatureMvtPaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureMvtPaieService);
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

    it('should create a NatureMvtPaie', () => {
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

      service.create(new NatureMvtPaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureMvtPaie', () => {
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

    it('should partial update a NatureMvtPaie', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
        },
        new NatureMvtPaie()
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

    it('should return a list of NatureMvtPaie', () => {
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

    it('should delete a NatureMvtPaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureMvtPaieToCollectionIfMissing', () => {
      it('should add a NatureMvtPaie to an empty array', () => {
        const natureMvtPaie: INatureMvtPaie = { id: 123 };
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing([], natureMvtPaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureMvtPaie);
      });

      it('should not add a NatureMvtPaie to an array that contains it', () => {
        const natureMvtPaie: INatureMvtPaie = { id: 123 };
        const natureMvtPaieCollection: INatureMvtPaie[] = [
          {
            ...natureMvtPaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing(natureMvtPaieCollection, natureMvtPaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureMvtPaie to an array that doesn't contain it", () => {
        const natureMvtPaie: INatureMvtPaie = { id: 123 };
        const natureMvtPaieCollection: INatureMvtPaie[] = [{ id: 456 }];
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing(natureMvtPaieCollection, natureMvtPaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureMvtPaie);
      });

      it('should add only unique NatureMvtPaie to an array', () => {
        const natureMvtPaieArray: INatureMvtPaie[] = [{ id: 123 }, { id: 456 }, { id: 59429 }];
        const natureMvtPaieCollection: INatureMvtPaie[] = [{ id: 123 }];
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing(natureMvtPaieCollection, ...natureMvtPaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureMvtPaie: INatureMvtPaie = { id: 123 };
        const natureMvtPaie2: INatureMvtPaie = { id: 456 };
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing([], natureMvtPaie, natureMvtPaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureMvtPaie);
        expect(expectedResult).toContain(natureMvtPaie2);
      });

      it('should accept null and undefined values', () => {
        const natureMvtPaie: INatureMvtPaie = { id: 123 };
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing([], null, natureMvtPaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureMvtPaie);
      });

      it('should return initial array if no NatureMvtPaie is added', () => {
        const natureMvtPaieCollection: INatureMvtPaie[] = [{ id: 123 }];
        expectedResult = service.addNatureMvtPaieToCollectionIfMissing(natureMvtPaieCollection, undefined, null);
        expect(expectedResult).toEqual(natureMvtPaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
