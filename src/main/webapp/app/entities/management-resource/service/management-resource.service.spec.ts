import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IManagementResource, ManagementResource } from '../management-resource.model';

import { ManagementResourceService } from './management-resource.service';

describe('ManagementResource Service', () => {
  let service: ManagementResourceService;
  let httpMock: HttpTestingController;
  let elemDefault: IManagementResource;
  let expectedResult: IManagementResource | IManagementResource[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ManagementResourceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      util: 'AAAAAAA',
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
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

    it('should create a ManagementResource', () => {
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

      service.create(new ManagementResource()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ManagementResource', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
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

    it('should partial update a ManagementResource', () => {
      const patchObject = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ManagementResource()
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

    it('should return a list of ManagementResource', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
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

    it('should delete a ManagementResource', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addManagementResourceToCollectionIfMissing', () => {
      it('should add a ManagementResource to an empty array', () => {
        const managementResource: IManagementResource = { id: 123 };
        expectedResult = service.addManagementResourceToCollectionIfMissing([], managementResource);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResource);
      });

      it('should not add a ManagementResource to an array that contains it', () => {
        const managementResource: IManagementResource = { id: 123 };
        const managementResourceCollection: IManagementResource[] = [
          {
            ...managementResource,
          },
          { id: 456 },
        ];
        expectedResult = service.addManagementResourceToCollectionIfMissing(managementResourceCollection, managementResource);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ManagementResource to an array that doesn't contain it", () => {
        const managementResource: IManagementResource = { id: 123 };
        const managementResourceCollection: IManagementResource[] = [{ id: 456 }];
        expectedResult = service.addManagementResourceToCollectionIfMissing(managementResourceCollection, managementResource);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResource);
      });

      it('should add only unique ManagementResource to an array', () => {
        const managementResourceArray: IManagementResource[] = [{ id: 123 }, { id: 456 }, { id: 48281 }];
        const managementResourceCollection: IManagementResource[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceToCollectionIfMissing(managementResourceCollection, ...managementResourceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const managementResource: IManagementResource = { id: 123 };
        const managementResource2: IManagementResource = { id: 456 };
        expectedResult = service.addManagementResourceToCollectionIfMissing([], managementResource, managementResource2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResource);
        expect(expectedResult).toContain(managementResource2);
      });

      it('should accept null and undefined values', () => {
        const managementResource: IManagementResource = { id: 123 };
        expectedResult = service.addManagementResourceToCollectionIfMissing([], null, managementResource, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResource);
      });

      it('should return initial array if no ManagementResource is added', () => {
        const managementResourceCollection: IManagementResource[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceToCollectionIfMissing(managementResourceCollection, undefined, null);
        expect(expectedResult).toEqual(managementResourceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
