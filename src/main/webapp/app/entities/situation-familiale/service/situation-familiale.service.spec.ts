import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISituationFamiliale, SituationFamiliale } from '../situation-familiale.model';

import { SituationFamilialeService } from './situation-familiale.service';

describe('SituationFamiliale Service', () => {
  let service: SituationFamilialeService;
  let httpMock: HttpTestingController;
  let elemDefault: ISituationFamiliale;
  let expectedResult: ISituationFamiliale | ISituationFamiliale[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SituationFamilialeService);
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

    it('should create a SituationFamiliale', () => {
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

      service.create(new SituationFamiliale()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SituationFamiliale', () => {
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

    it('should partial update a SituationFamiliale', () => {
      const patchObject = Object.assign(
        {
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SituationFamiliale()
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

    it('should return a list of SituationFamiliale', () => {
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

    it('should delete a SituationFamiliale', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSituationFamilialeToCollectionIfMissing', () => {
      it('should add a SituationFamiliale to an empty array', () => {
        const situationFamiliale: ISituationFamiliale = { id: 123 };
        expectedResult = service.addSituationFamilialeToCollectionIfMissing([], situationFamiliale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situationFamiliale);
      });

      it('should not add a SituationFamiliale to an array that contains it', () => {
        const situationFamiliale: ISituationFamiliale = { id: 123 };
        const situationFamilialeCollection: ISituationFamiliale[] = [
          {
            ...situationFamiliale,
          },
          { id: 456 },
        ];
        expectedResult = service.addSituationFamilialeToCollectionIfMissing(situationFamilialeCollection, situationFamiliale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SituationFamiliale to an array that doesn't contain it", () => {
        const situationFamiliale: ISituationFamiliale = { id: 123 };
        const situationFamilialeCollection: ISituationFamiliale[] = [{ id: 456 }];
        expectedResult = service.addSituationFamilialeToCollectionIfMissing(situationFamilialeCollection, situationFamiliale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situationFamiliale);
      });

      it('should add only unique SituationFamiliale to an array', () => {
        const situationFamilialeArray: ISituationFamiliale[] = [{ id: 123 }, { id: 456 }, { id: 92329 }];
        const situationFamilialeCollection: ISituationFamiliale[] = [{ id: 123 }];
        expectedResult = service.addSituationFamilialeToCollectionIfMissing(situationFamilialeCollection, ...situationFamilialeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const situationFamiliale: ISituationFamiliale = { id: 123 };
        const situationFamiliale2: ISituationFamiliale = { id: 456 };
        expectedResult = service.addSituationFamilialeToCollectionIfMissing([], situationFamiliale, situationFamiliale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situationFamiliale);
        expect(expectedResult).toContain(situationFamiliale2);
      });

      it('should accept null and undefined values', () => {
        const situationFamiliale: ISituationFamiliale = { id: 123 };
        expectedResult = service.addSituationFamilialeToCollectionIfMissing([], null, situationFamiliale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situationFamiliale);
      });

      it('should return initial array if no SituationFamiliale is added', () => {
        const situationFamilialeCollection: ISituationFamiliale[] = [{ id: 123 }];
        expectedResult = service.addSituationFamilialeToCollectionIfMissing(situationFamilialeCollection, undefined, null);
        expect(expectedResult).toEqual(situationFamilialeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
