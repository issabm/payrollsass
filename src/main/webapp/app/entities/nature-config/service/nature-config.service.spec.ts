import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureConfig, NatureConfig } from '../nature-config.model';

import { NatureConfigService } from './nature-config.service';

describe('NatureConfig Service', () => {
  let service: NatureConfigService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureConfig;
  let expectedResult: INatureConfig | INatureConfig[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureConfigService);
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

    it('should create a NatureConfig', () => {
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

      service.create(new NatureConfig()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureConfig', () => {
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

    it('should partial update a NatureConfig', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          libFr: 'BBBBBB',
          isDeleted: true,
        },
        new NatureConfig()
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

    it('should return a list of NatureConfig', () => {
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

    it('should delete a NatureConfig', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureConfigToCollectionIfMissing', () => {
      it('should add a NatureConfig to an empty array', () => {
        const natureConfig: INatureConfig = { id: 123 };
        expectedResult = service.addNatureConfigToCollectionIfMissing([], natureConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureConfig);
      });

      it('should not add a NatureConfig to an array that contains it', () => {
        const natureConfig: INatureConfig = { id: 123 };
        const natureConfigCollection: INatureConfig[] = [
          {
            ...natureConfig,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureConfigToCollectionIfMissing(natureConfigCollection, natureConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureConfig to an array that doesn't contain it", () => {
        const natureConfig: INatureConfig = { id: 123 };
        const natureConfigCollection: INatureConfig[] = [{ id: 456 }];
        expectedResult = service.addNatureConfigToCollectionIfMissing(natureConfigCollection, natureConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureConfig);
      });

      it('should add only unique NatureConfig to an array', () => {
        const natureConfigArray: INatureConfig[] = [{ id: 123 }, { id: 456 }, { id: 53227 }];
        const natureConfigCollection: INatureConfig[] = [{ id: 123 }];
        expectedResult = service.addNatureConfigToCollectionIfMissing(natureConfigCollection, ...natureConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureConfig: INatureConfig = { id: 123 };
        const natureConfig2: INatureConfig = { id: 456 };
        expectedResult = service.addNatureConfigToCollectionIfMissing([], natureConfig, natureConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureConfig);
        expect(expectedResult).toContain(natureConfig2);
      });

      it('should accept null and undefined values', () => {
        const natureConfig: INatureConfig = { id: 123 };
        expectedResult = service.addNatureConfigToCollectionIfMissing([], null, natureConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureConfig);
      });

      it('should return initial array if no NatureConfig is added', () => {
        const natureConfigCollection: INatureConfig[] = [{ id: 123 }];
        expectedResult = service.addNatureConfigToCollectionIfMissing(natureConfigCollection, undefined, null);
        expect(expectedResult).toEqual(natureConfigCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
