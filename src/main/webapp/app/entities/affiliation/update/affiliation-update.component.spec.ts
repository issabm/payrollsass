jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AffiliationService } from '../service/affiliation.service';
import { IAffiliation, Affiliation } from '../affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

import { AffiliationUpdateComponent } from './affiliation-update.component';

describe('Affiliation Management Update Component', () => {
  let comp: AffiliationUpdateComponent;
  let fixture: ComponentFixture<AffiliationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let paysService: PaysService;
  let deviseService: DeviseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AffiliationUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AffiliationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AffiliationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    paysService = TestBed.inject(PaysService);
    deviseService = TestBed.inject(DeviseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entreprise query and add missing value', () => {
      const affiliation: IAffiliation = { id: 456 };
      const entreprise: IEntreprise = { id: 24845 };
      affiliation.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 7024 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const affiliation: IAffiliation = { id: 456 };
      const direction: IAffiliation = { id: 47923 };
      affiliation.direction = direction;

      const affiliationCollection: IAffiliation[] = [{ id: 47060 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [direction];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const affiliation: IAffiliation = { id: 456 };
      const pays: IPays = { id: 61459 };
      affiliation.pays = pays;

      const paysCollection: IPays[] = [{ id: 40737 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Devise query and add missing value', () => {
      const affiliation: IAffiliation = { id: 456 };
      const devise: IDevise = { id: 27209 };
      affiliation.devise = devise;

      const deviseCollection: IDevise[] = [{ id: 86889 }];
      jest.spyOn(deviseService, 'query').mockReturnValue(of(new HttpResponse({ body: deviseCollection })));
      const additionalDevises = [devise];
      const expectedCollection: IDevise[] = [...additionalDevises, ...deviseCollection];
      jest.spyOn(deviseService, 'addDeviseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      expect(deviseService.query).toHaveBeenCalled();
      expect(deviseService.addDeviseToCollectionIfMissing).toHaveBeenCalledWith(deviseCollection, ...additionalDevises);
      expect(comp.devisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const affiliation: IAffiliation = { id: 456 };
      const entreprise: IEntreprise = { id: 11665 };
      affiliation.entreprise = entreprise;
      const direction: IAffiliation = { id: 52850 };
      affiliation.direction = direction;
      const pays: IPays = { id: 97947 };
      affiliation.pays = pays;
      const devise: IDevise = { id: 28780 };
      affiliation.devise = devise;

      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(affiliation));
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.affiliationsSharedCollection).toContain(direction);
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.devisesSharedCollection).toContain(devise);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliation>>();
      const affiliation = { id: 123 };
      jest.spyOn(affiliationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affiliation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(affiliationService.update).toHaveBeenCalledWith(affiliation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliation>>();
      const affiliation = new Affiliation();
      jest.spyOn(affiliationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affiliation }));
      saveSubject.complete();

      // THEN
      expect(affiliationService.create).toHaveBeenCalledWith(affiliation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliation>>();
      const affiliation = { id: 123 };
      jest.spyOn(affiliationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(affiliationService.update).toHaveBeenCalledWith(affiliation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAffiliationById', () => {
      it('Should return tracked Affiliation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffiliationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDeviseById', () => {
      it('Should return tracked Devise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
