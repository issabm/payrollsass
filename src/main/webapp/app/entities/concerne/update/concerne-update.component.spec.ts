jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ConcerneService } from '../service/concerne.service';
import { IConcerne, Concerne } from '../concerne.model';

import { ConcerneUpdateComponent } from './concerne-update.component';

describe('Concerne Management Update Component', () => {
  let comp: ConcerneUpdateComponent;
  let fixture: ComponentFixture<ConcerneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let concerneService: ConcerneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ConcerneUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ConcerneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConcerneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    concerneService = TestBed.inject(ConcerneService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const concerne: IConcerne = { id: 456 };

      activatedRoute.data = of({ concerne });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(concerne));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concerne>>();
      const concerne = { id: 123 };
      jest.spyOn(concerneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concerne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concerne }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(concerneService.update).toHaveBeenCalledWith(concerne);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concerne>>();
      const concerne = new Concerne();
      jest.spyOn(concerneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concerne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concerne }));
      saveSubject.complete();

      // THEN
      expect(concerneService.create).toHaveBeenCalledWith(concerne);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concerne>>();
      const concerne = { id: 123 };
      jest.spyOn(concerneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concerne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(concerneService.update).toHaveBeenCalledWith(concerne);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
