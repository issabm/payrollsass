jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureConfigService } from '../service/nature-config.service';
import { INatureConfig, NatureConfig } from '../nature-config.model';

import { NatureConfigUpdateComponent } from './nature-config-update.component';

describe('NatureConfig Management Update Component', () => {
  let comp: NatureConfigUpdateComponent;
  let fixture: ComponentFixture<NatureConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureConfigService: NatureConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureConfigUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureConfigService = TestBed.inject(NatureConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureConfig: INatureConfig = { id: 456 };

      activatedRoute.data = of({ natureConfig });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureConfig));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureConfig>>();
      const natureConfig = { id: 123 };
      jest.spyOn(natureConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureConfig }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureConfigService.update).toHaveBeenCalledWith(natureConfig);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureConfig>>();
      const natureConfig = new NatureConfig();
      jest.spyOn(natureConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureConfig }));
      saveSubject.complete();

      // THEN
      expect(natureConfigService.create).toHaveBeenCalledWith(natureConfig);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureConfig>>();
      const natureConfig = { id: 123 };
      jest.spyOn(natureConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureConfigService.update).toHaveBeenCalledWith(natureConfig);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
