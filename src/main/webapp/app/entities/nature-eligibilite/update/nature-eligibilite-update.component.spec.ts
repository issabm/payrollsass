jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureEligibiliteService } from '../service/nature-eligibilite.service';
import { INatureEligibilite, NatureEligibilite } from '../nature-eligibilite.model';

import { NatureEligibiliteUpdateComponent } from './nature-eligibilite-update.component';

describe('NatureEligibilite Management Update Component', () => {
  let comp: NatureEligibiliteUpdateComponent;
  let fixture: ComponentFixture<NatureEligibiliteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureEligibiliteService: NatureEligibiliteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureEligibiliteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureEligibiliteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureEligibiliteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureEligibiliteService = TestBed.inject(NatureEligibiliteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureEligibilite: INatureEligibilite = { id: 456 };

      activatedRoute.data = of({ natureEligibilite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureEligibilite));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureEligibilite>>();
      const natureEligibilite = { id: 123 };
      jest.spyOn(natureEligibiliteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureEligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureEligibilite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureEligibiliteService.update).toHaveBeenCalledWith(natureEligibilite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureEligibilite>>();
      const natureEligibilite = new NatureEligibilite();
      jest.spyOn(natureEligibiliteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureEligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureEligibilite }));
      saveSubject.complete();

      // THEN
      expect(natureEligibiliteService.create).toHaveBeenCalledWith(natureEligibilite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureEligibilite>>();
      const natureEligibilite = { id: 123 };
      jest.spyOn(natureEligibiliteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureEligibilite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureEligibiliteService.update).toHaveBeenCalledWith(natureEligibilite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
