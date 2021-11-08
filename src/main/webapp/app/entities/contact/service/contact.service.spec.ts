import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContact, Contact } from '../contact.model';

import { ContactService } from './contact.service';

describe('Contact Service', () => {
  let service: ContactService;
  let httpMock: HttpTestingController;
  let elemDefault: IContact;
  let expectedResult: IContact | IContact[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nomAr: 'AAAAAAA',
      prenomAr: 'AAAAAAA',
      nomEn: 'AAAAAAA',
      prenomEn: 'AAAAAAA',
      email: 'AAAAAAA',
      tel: 'AAAAAAA',
      phone: 'AAAAAAA',
      adresse: 'AAAAAAA',
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

    it('should create a Contact', () => {
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

      service.create(new Contact()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contact', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomAr: 'BBBBBB',
          prenomAr: 'BBBBBB',
          nomEn: 'BBBBBB',
          prenomEn: 'BBBBBB',
          email: 'BBBBBB',
          tel: 'BBBBBB',
          phone: 'BBBBBB',
          adresse: 'BBBBBB',
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

    it('should partial update a Contact', () => {
      const patchObject = Object.assign(
        {
          nomAr: 'BBBBBB',
          nomEn: 'BBBBBB',
          phone: 'BBBBBB',
          adresse: 'BBBBBB',
        },
        new Contact()
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

    it('should return a list of Contact', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomAr: 'BBBBBB',
          prenomAr: 'BBBBBB',
          nomEn: 'BBBBBB',
          prenomEn: 'BBBBBB',
          email: 'BBBBBB',
          tel: 'BBBBBB',
          phone: 'BBBBBB',
          adresse: 'BBBBBB',
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

    it('should delete a Contact', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContactToCollectionIfMissing', () => {
      it('should add a Contact to an empty array', () => {
        const contact: IContact = { id: 123 };
        expectedResult = service.addContactToCollectionIfMissing([], contact);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contact);
      });

      it('should not add a Contact to an array that contains it', () => {
        const contact: IContact = { id: 123 };
        const contactCollection: IContact[] = [
          {
            ...contact,
          },
          { id: 456 },
        ];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, contact);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contact to an array that doesn't contain it", () => {
        const contact: IContact = { id: 123 };
        const contactCollection: IContact[] = [{ id: 456 }];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, contact);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contact);
      });

      it('should add only unique Contact to an array', () => {
        const contactArray: IContact[] = [{ id: 123 }, { id: 456 }, { id: 87161 }];
        const contactCollection: IContact[] = [{ id: 123 }];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, ...contactArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contact: IContact = { id: 123 };
        const contact2: IContact = { id: 456 };
        expectedResult = service.addContactToCollectionIfMissing([], contact, contact2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contact);
        expect(expectedResult).toContain(contact2);
      });

      it('should accept null and undefined values', () => {
        const contact: IContact = { id: 123 };
        expectedResult = service.addContactToCollectionIfMissing([], null, contact, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contact);
      });

      it('should return initial array if no Contact is added', () => {
        const contactCollection: IContact[] = [{ id: 123 }];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, undefined, null);
        expect(expectedResult).toEqual(contactCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
