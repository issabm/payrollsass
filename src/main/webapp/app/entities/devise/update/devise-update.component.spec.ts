jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DeviseService } from '../service/devise.service';
import { IDevise, Devise } from '../devise.model';

import { DeviseUpdateComponent } from './devise-update.component';

describe('Devise Management Update Component', () => {
  let comp: DeviseUpdateComponent;
  let fixture: ComponentFixture<DeviseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deviseService: DeviseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DeviseUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DeviseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deviseService = TestBed.inject(DeviseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const devise: IDevise = { id: 456 };

      activatedRoute.data = of({ devise });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(devise));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Devise>>();
      const devise = { id: 123 };
      jest.spyOn(deviseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: devise }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(deviseService.update).toHaveBeenCalledWith(devise);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Devise>>();
      const devise = new Devise();
      jest.spyOn(deviseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: devise }));
      saveSubject.complete();

      // THEN
      expect(deviseService.create).toHaveBeenCalledWith(devise);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Devise>>();
      const devise = { id: 123 };
      jest.spyOn(deviseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deviseService.update).toHaveBeenCalledWith(devise);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
