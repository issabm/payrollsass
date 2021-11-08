import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDemandeCalculPaie, DemandeCalculPaie } from '../demande-calcul-paie.model';

import { DemandeCalculPaieService } from './demande-calcul-paie.service';

describe('DemandeCalculPaie Service', () => {
  let service: DemandeCalculPaieService;
  let httpMock: HttpTestingController;
  let elemDefault: IDemandeCalculPaie;
  let expectedResult: IDemandeCalculPaie | IDemandeCalculPaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeCalculPaieService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      lib: 'AAAAAAA',
      annee: 0,
      mois: 0,
      dateCalcul: currentDate,
      dateValid: currentDate,
      datePayroll: currentDate,
      totalNet: 0,
      totalNetDevise: 0,
      tauxChange: 0,
      calculBy: 'AAAAAAA',
      effectBy: 'AAAAAAA',
      dateSituation: currentDate,
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
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
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

    it('should create a DemandeCalculPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new DemandeCalculPaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeCalculPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          annee: 1,
          mois: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          totalNet: 1,
          totalNetDevise: 1,
          tauxChange: 1,
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
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
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
          dateSituation: currentDate,
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

    it('should partial update a DemandeCalculPaie', () => {
      const patchObject = Object.assign(
        {
          annee: 1,
          mois: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          tauxChange: 1,
          effectBy: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new DemandeCalculPaie()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
          dateSituation: currentDate,
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

    it('should return a list of DemandeCalculPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          annee: 1,
          mois: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          totalNet: 1,
          totalNetDevise: 1,
          tauxChange: 1,
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
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
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
          dateSituation: currentDate,
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

    it('should delete a DemandeCalculPaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDemandeCalculPaieToCollectionIfMissing', () => {
      it('should add a DemandeCalculPaie to an empty array', () => {
        const demandeCalculPaie: IDemandeCalculPaie = { id: 123 };
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing([], demandeCalculPaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeCalculPaie);
      });

      it('should not add a DemandeCalculPaie to an array that contains it', () => {
        const demandeCalculPaie: IDemandeCalculPaie = { id: 123 };
        const demandeCalculPaieCollection: IDemandeCalculPaie[] = [
          {
            ...demandeCalculPaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing(demandeCalculPaieCollection, demandeCalculPaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeCalculPaie to an array that doesn't contain it", () => {
        const demandeCalculPaie: IDemandeCalculPaie = { id: 123 };
        const demandeCalculPaieCollection: IDemandeCalculPaie[] = [{ id: 456 }];
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing(demandeCalculPaieCollection, demandeCalculPaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeCalculPaie);
      });

      it('should add only unique DemandeCalculPaie to an array', () => {
        const demandeCalculPaieArray: IDemandeCalculPaie[] = [{ id: 123 }, { id: 456 }, { id: 29809 }];
        const demandeCalculPaieCollection: IDemandeCalculPaie[] = [{ id: 123 }];
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing(demandeCalculPaieCollection, ...demandeCalculPaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeCalculPaie: IDemandeCalculPaie = { id: 123 };
        const demandeCalculPaie2: IDemandeCalculPaie = { id: 456 };
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing([], demandeCalculPaie, demandeCalculPaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeCalculPaie);
        expect(expectedResult).toContain(demandeCalculPaie2);
      });

      it('should accept null and undefined values', () => {
        const demandeCalculPaie: IDemandeCalculPaie = { id: 123 };
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing([], null, demandeCalculPaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeCalculPaie);
      });

      it('should return initial array if no DemandeCalculPaie is added', () => {
        const demandeCalculPaieCollection: IDemandeCalculPaie[] = [{ id: 123 }];
        expectedResult = service.addDemandeCalculPaieToCollectionIfMissing(demandeCalculPaieCollection, undefined, null);
        expect(expectedResult).toEqual(demandeCalculPaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
