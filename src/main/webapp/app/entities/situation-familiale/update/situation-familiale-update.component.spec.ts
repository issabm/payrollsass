jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SituationFamilialeService } from '../service/situation-familiale.service';
import { ISituationFamiliale, SituationFamiliale } from '../situation-familiale.model';

import { SituationFamilialeUpdateComponent } from './situation-familiale-update.component';

describe('SituationFamiliale Management Update Component', () => {
  let comp: SituationFamilialeUpdateComponent;
  let fixture: ComponentFixture<SituationFamilialeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let situationFamilialeService: SituationFamilialeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SituationFamilialeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SituationFamilialeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SituationFamilialeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    situationFamilialeService = TestBed.inject(SituationFamilialeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const situationFamiliale: ISituationFamiliale = { id: 456 };

      activatedRoute.data = of({ situationFamiliale });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(situationFamiliale));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SituationFamiliale>>();
      const situationFamiliale = { id: 123 };
      jest.spyOn(situationFamilialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationFamiliale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situationFamiliale }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(situationFamilialeService.update).toHaveBeenCalledWith(situationFamiliale);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SituationFamiliale>>();
      const situationFamiliale = new SituationFamiliale();
      jest.spyOn(situationFamilialeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationFamiliale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situationFamiliale }));
      saveSubject.complete();

      // THEN
      expect(situationFamilialeService.create).toHaveBeenCalledWith(situationFamiliale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SituationFamiliale>>();
      const situationFamiliale = { id: 123 };
      jest.spyOn(situationFamilialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationFamiliale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(situationFamilialeService.update).toHaveBeenCalledWith(situationFamiliale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
