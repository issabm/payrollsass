jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EntrepriseService } from '../service/entreprise.service';
import { IEntreprise, Entreprise } from '../entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

import { EntrepriseUpdateComponent } from './entreprise-update.component';

describe('Entreprise Management Update Component', () => {
  let comp: EntrepriseUpdateComponent;
  let fixture: ComponentFixture<EntrepriseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let paysService: PaysService;
  let deviseService: DeviseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EntrepriseUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EntrepriseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntrepriseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    paysService = TestBed.inject(PaysService);
    deviseService = TestBed.inject(DeviseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Groupe query and add missing value', () => {
      const entreprise: IEntreprise = { id: 456 };
      const groupe: IGroupe = { id: 44014 };
      entreprise.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 24002 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const entreprise: IEntreprise = { id: 456 };
      const pays: IPays = { id: 57437 };
      entreprise.pays = pays;

      const paysCollection: IPays[] = [{ id: 17141 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const entreprise: IEntreprise = { id: 456 };
      const mere: IEntreprise = { id: 76669 };
      entreprise.mere = mere;

      const entrepriseCollection: IEntreprise[] = [{ id: 78753 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [mere];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Devise query and add missing value', () => {
      const entreprise: IEntreprise = { id: 456 };
      const devise: IDevise = { id: 63192 };
      entreprise.devise = devise;

      const deviseCollection: IDevise[] = [{ id: 85785 }];
      jest.spyOn(deviseService, 'query').mockReturnValue(of(new HttpResponse({ body: deviseCollection })));
      const additionalDevises = [devise];
      const expectedCollection: IDevise[] = [...additionalDevises, ...deviseCollection];
      jest.spyOn(deviseService, 'addDeviseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      expect(deviseService.query).toHaveBeenCalled();
      expect(deviseService.addDeviseToCollectionIfMissing).toHaveBeenCalledWith(deviseCollection, ...additionalDevises);
      expect(comp.devisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const entreprise: IEntreprise = { id: 456 };
      const groupe: IGroupe = { id: 97456 };
      entreprise.groupe = groupe;
      const pays: IPays = { id: 51883 };
      entreprise.pays = pays;
      const mere: IEntreprise = { id: 56630 };
      entreprise.mere = mere;
      const devise: IDevise = { id: 19512 };
      entreprise.devise = devise;

      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(entreprise));
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.entreprisesSharedCollection).toContain(mere);
      expect(comp.devisesSharedCollection).toContain(devise);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entreprise>>();
      const entreprise = { id: 123 };
      jest.spyOn(entrepriseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entreprise }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(entrepriseService.update).toHaveBeenCalledWith(entreprise);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entreprise>>();
      const entreprise = new Entreprise();
      jest.spyOn(entrepriseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entreprise }));
      saveSubject.complete();

      // THEN
      expect(entrepriseService.create).toHaveBeenCalledWith(entreprise);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entreprise>>();
      const entreprise = { id: 123 };
      jest.spyOn(entrepriseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entreprise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entrepriseService.update).toHaveBeenCalledWith(entreprise);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackGroupeById', () => {
      it('Should return tracked Groupe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGroupeById(0, entity);
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

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
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
