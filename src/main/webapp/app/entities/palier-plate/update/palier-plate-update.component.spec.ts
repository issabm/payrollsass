jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PalierPlateService } from '../service/palier-plate.service';
import { IPalierPlate, PalierPlate } from '../palier-plate.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IPlate } from 'app/entities/plate/plate.model';
import { PlateService } from 'app/entities/plate/service/plate.service';

import { PalierPlateUpdateComponent } from './palier-plate-update.component';

describe('PalierPlate Management Update Component', () => {
  let comp: PalierPlateUpdateComponent;
  let fixture: ComponentFixture<PalierPlateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let palierPlateService: PalierPlateService;
  let paysService: PaysService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let plateService: PlateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PalierPlateUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PalierPlateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PalierPlateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    palierPlateService = TestBed.inject(PalierPlateService);
    paysService = TestBed.inject(PaysService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    plateService = TestBed.inject(PlateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const pays: IPays = { id: 16686 };
      palierPlate.pays = pays;

      const paysCollection: IPays[] = [{ id: 97069 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const affilication: IAffiliation = { id: 12387 };
      palierPlate.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 90324 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const entreprise: IEntreprise = { id: 12237 };
      palierPlate.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 85085 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const groupe: IGroupe = { id: 96921 };
      palierPlate.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 24004 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Plate query and add missing value', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const plateTarget: IPlate = { id: 73283 };
      palierPlate.plateTarget = plateTarget;
      const platBaseCalcul: IPlate = { id: 23026 };
      palierPlate.platBaseCalcul = platBaseCalcul;

      const plateCollection: IPlate[] = [{ id: 20986 }];
      jest.spyOn(plateService, 'query').mockReturnValue(of(new HttpResponse({ body: plateCollection })));
      const additionalPlates = [plateTarget, platBaseCalcul];
      const expectedCollection: IPlate[] = [...additionalPlates, ...plateCollection];
      jest.spyOn(plateService, 'addPlateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(plateService.query).toHaveBeenCalled();
      expect(plateService.addPlateToCollectionIfMissing).toHaveBeenCalledWith(plateCollection, ...additionalPlates);
      expect(comp.platesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const palierPlate: IPalierPlate = { id: 456 };
      const pays: IPays = { id: 31828 };
      palierPlate.pays = pays;
      const affilication: IAffiliation = { id: 77627 };
      palierPlate.affilication = affilication;
      const entreprise: IEntreprise = { id: 28417 };
      palierPlate.entreprise = entreprise;
      const groupe: IGroupe = { id: 75861 };
      palierPlate.groupe = groupe;
      const plateTarget: IPlate = { id: 12550 };
      palierPlate.plateTarget = plateTarget;
      const platBaseCalcul: IPlate = { id: 26568 };
      palierPlate.platBaseCalcul = platBaseCalcul;

      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(palierPlate));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.platesSharedCollection).toContain(plateTarget);
      expect(comp.platesSharedCollection).toContain(platBaseCalcul);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierPlate>>();
      const palierPlate = { id: 123 };
      jest.spyOn(palierPlateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierPlate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(palierPlateService.update).toHaveBeenCalledWith(palierPlate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierPlate>>();
      const palierPlate = new PalierPlate();
      jest.spyOn(palierPlateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierPlate }));
      saveSubject.complete();

      // THEN
      expect(palierPlateService.create).toHaveBeenCalledWith(palierPlate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierPlate>>();
      const palierPlate = { id: 123 };
      jest.spyOn(palierPlateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierPlate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(palierPlateService.update).toHaveBeenCalledWith(palierPlate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
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

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGroupeById', () => {
      it('Should return tracked Groupe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGroupeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPlateById', () => {
      it('Should return tracked Plate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
