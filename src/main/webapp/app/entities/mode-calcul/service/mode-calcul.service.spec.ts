import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IModeCalcul, ModeCalcul } from '../mode-calcul.model';

import { ModeCalculService } from './mode-calcul.service';

describe('ModeCalcul Service', () => {
  let service: ModeCalculService;
  let httpMock: HttpTestingController;
  let elemDefault: IModeCalcul;
  let expectedResult: IModeCalcul | IModeCalcul[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ModeCalculService);
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

    it('should create a ModeCalcul', () => {
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

      service.create(new ModeCalcul()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModeCalcul', () => {
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

    it('should partial update a ModeCalcul', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ModeCalcul()
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

    it('should return a list of ModeCalcul', () => {
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

    it('should delete a ModeCalcul', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addModeCalculToCollectionIfMissing', () => {
      it('should add a ModeCalcul to an empty array', () => {
        const modeCalcul: IModeCalcul = { id: 123 };
        expectedResult = service.addModeCalculToCollectionIfMissing([], modeCalcul);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modeCalcul);
      });

      it('should not add a ModeCalcul to an array that contains it', () => {
        const modeCalcul: IModeCalcul = { id: 123 };
        const modeCalculCollection: IModeCalcul[] = [
          {
            ...modeCalcul,
          },
          { id: 456 },
        ];
        expectedResult = service.addModeCalculToCollectionIfMissing(modeCalculCollection, modeCalcul);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModeCalcul to an array that doesn't contain it", () => {
        const modeCalcul: IModeCalcul = { id: 123 };
        const modeCalculCollection: IModeCalcul[] = [{ id: 456 }];
        expectedResult = service.addModeCalculToCollectionIfMissing(modeCalculCollection, modeCalcul);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modeCalcul);
      });

      it('should add only unique ModeCalcul to an array', () => {
        const modeCalculArray: IModeCalcul[] = [{ id: 123 }, { id: 456 }, { id: 80973 }];
        const modeCalculCollection: IModeCalcul[] = [{ id: 123 }];
        expectedResult = service.addModeCalculToCollectionIfMissing(modeCalculCollection, ...modeCalculArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modeCalcul: IModeCalcul = { id: 123 };
        const modeCalcul2: IModeCalcul = { id: 456 };
        expectedResult = service.addModeCalculToCollectionIfMissing([], modeCalcul, modeCalcul2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modeCalcul);
        expect(expectedResult).toContain(modeCalcul2);
      });

      it('should accept null and undefined values', () => {
        const modeCalcul: IModeCalcul = { id: 123 };
        expectedResult = service.addModeCalculToCollectionIfMissing([], null, modeCalcul, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modeCalcul);
      });

      it('should return initial array if no ModeCalcul is added', () => {
        const modeCalculCollection: IModeCalcul[] = [{ id: 123 }];
        expectedResult = service.addModeCalculToCollectionIfMissing(modeCalculCollection, undefined, null);
        expect(expectedResult).toEqual(modeCalculCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
