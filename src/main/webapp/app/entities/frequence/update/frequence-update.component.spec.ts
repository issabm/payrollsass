jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FrequenceService } from '../service/frequence.service';
import { IFrequence, Frequence } from '../frequence.model';

import { FrequenceUpdateComponent } from './frequence-update.component';

describe('Frequence Management Update Component', () => {
  let comp: FrequenceUpdateComponent;
  let fixture: ComponentFixture<FrequenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let frequenceService: FrequenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FrequenceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FrequenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FrequenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    frequenceService = TestBed.inject(FrequenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const frequence: IFrequence = { id: 456 };

      activatedRoute.data = of({ frequence });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(frequence));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Frequence>>();
      const frequence = { id: 123 };
      jest.spyOn(frequenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: frequence }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(frequenceService.update).toHaveBeenCalledWith(frequence);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Frequence>>();
      const frequence = new Frequence();
      jest.spyOn(frequenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: frequence }));
      saveSubject.complete();

      // THEN
      expect(frequenceService.create).toHaveBeenCalledWith(frequence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Frequence>>();
      const frequence = { id: 123 };
      jest.spyOn(frequenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(frequenceService.update).toHaveBeenCalledWith(frequence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
