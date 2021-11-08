import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAffiliation, Affiliation } from '../affiliation.model';

import { AffiliationService } from './affiliation.service';

describe('Affiliation Service', () => {
  let service: AffiliationService;
  let httpMock: HttpTestingController;
  let elemDefault: IAffiliation;
  let expectedResult: IAffiliation | IAffiliation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AffiliationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      tel: 'AAAAAAA',
      email: 'AAAAAAA',
      fax: 'AAAAAAA',
      util: 'AAAAAAA',
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      op: 'AAAAAAA',
      isDeleted: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Affiliation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
        },
        returnedFromService
      );

      service.create(new Affiliation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Affiliation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          tel: 'BBBBBB',
          email: 'BBBBBB',
          fax: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Affiliation', () => {
      const patchObject = Object.assign(
        {
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          fax: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
        },
        new Affiliation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateop: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Affiliation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          tel: 'BBBBBB',
          email: 'BBBBBB',
          fax: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Affiliation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAffiliationToCollectionIfMissing', () => {
      it('should add a Affiliation to an empty array', () => {
        const affiliation: IAffiliation = { id: 123 };
        expectedResult = service.addAffiliationToCollectionIfMissing([], affiliation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affiliation);
      });

      it('should not add a Affiliation to an array that contains it', () => {
        const affiliation: IAffiliation = { id: 123 };
        const affiliationCollection: IAffiliation[] = [
          {
            ...affiliation,
          },
          { id: 456 },
        ];
        expectedResult = service.addAffiliationToCollectionIfMissing(affiliationCollection, affiliation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Affiliation to an array that doesn't contain it", () => {
        const affiliation: IAffiliation = { id: 123 };
        const affiliationCollection: IAffiliation[] = [{ id: 456 }];
        expectedResult = service.addAffiliationToCollectionIfMissing(affiliationCollection, affiliation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affiliation);
      });

      it('should add only unique Affiliation to an array', () => {
        const affiliationArray: IAffiliation[] = [{ id: 123 }, { id: 456 }, { id: 60093 }];
        const affiliationCollection: IAffiliation[] = [{ id: 123 }];
        expectedResult = service.addAffiliationToCollectionIfMissing(affiliationCollection, ...affiliationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const affiliation: IAffiliation = { id: 123 };
        const affiliation2: IAffiliation = { id: 456 };
        expectedResult = service.addAffiliationToCollectionIfMissing([], affiliation, affiliation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affiliation);
        expect(expectedResult).toContain(affiliation2);
      });

      it('should accept null and undefined values', () => {
        const affiliation: IAffiliation = { id: 123 };
        expectedResult = service.addAffiliationToCollectionIfMissing([], null, affiliation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affiliation);
      });

      it('should return initial array if no Affiliation is added', () => {
        const affiliationCollection: IAffiliation[] = [{ id: 123 }];
        expectedResult = service.addAffiliationToCollectionIfMissing(affiliationCollection, undefined, null);
        expect(expectedResult).toEqual(affiliationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
