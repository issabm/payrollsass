jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureAdhesionService } from '../service/nature-adhesion.service';
import { INatureAdhesion, NatureAdhesion } from '../nature-adhesion.model';

import { NatureAdhesionUpdateComponent } from './nature-adhesion-update.component';

describe('NatureAdhesion Management Update Component', () => {
  let comp: NatureAdhesionUpdateComponent;
  let fixture: ComponentFixture<NatureAdhesionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureAdhesionService: NatureAdhesionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureAdhesionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureAdhesionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureAdhesionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureAdhesionService = TestBed.inject(NatureAdhesionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureAdhesion: INatureAdhesion = { id: 456 };

      activatedRoute.data = of({ natureAdhesion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureAdhesion));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAdhesion>>();
      const natureAdhesion = { id: 123 };
      jest.spyOn(natureAdhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAdhesion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureAdhesionService.update).toHaveBeenCalledWith(natureAdhesion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAdhesion>>();
      const natureAdhesion = new NatureAdhesion();
      jest.spyOn(natureAdhesionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAdhesion }));
      saveSubject.complete();

      // THEN
      expect(natureAdhesionService.create).toHaveBeenCalledWith(natureAdhesion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAdhesion>>();
      const natureAdhesion = { id: 123 };
      jest.spyOn(natureAdhesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAdhesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureAdhesionService.update).toHaveBeenCalledWith(natureAdhesion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
